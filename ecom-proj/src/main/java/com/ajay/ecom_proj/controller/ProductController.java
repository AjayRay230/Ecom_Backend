package com.ajay.ecom_proj.controller;

import com.ajay.ecom_proj.model.Product;
import com.ajay.ecom_proj.service.ProductService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService service;
    @RequestMapping("/")
    public String greeting() {
        return "Hello World";
    }
    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProducts() {
        return  new ResponseEntity<>( service.getAllProducts(), HttpStatus.OK);

    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct( @PathVariable int id) {

        Product product = service.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
// we are not sure what we are going to return we might return data or status
        @PostMapping("/product")
      public ResponseEntity<?>  addProduct(@RequestPart("product") @Valid @NonNull Product product ,
                                                    @RequestPart("imageFile") MultipartFile imageFile)

        {
           try {
               Product product1 = service.addProduct(product, imageFile);
               return new ResponseEntity<>(product1, HttpStatus.CREATED);
           }
           catch(Exception e)
           {
               return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           }

        }
        

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable("id") int productId )
    {
        Product product = service.getProductById(productId);
        if(product == null||product.getImageData()==null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(product.getImageType()))
                .body(product.getImageData());


    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String>  updateProduct(@PathVariable int id, @RequestPart Product product,@RequestPart MultipartFile imageFile) throws IOException {
        Product product1 = service.updateProduct(id,product,imageFile);
        if (product1 != null) {
            return new ResponseEntity<>(product1.getName(), HttpStatus.OK);
        }
        else  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id)
    {
        Product product = service.getProductById(id);
        if(product != null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/product/search")

    public ResponseEntity<List<Product>> searchProduct(String keyword) {
        System.out.println("searching with..." + keyword);
        List<Product>  products = service.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
