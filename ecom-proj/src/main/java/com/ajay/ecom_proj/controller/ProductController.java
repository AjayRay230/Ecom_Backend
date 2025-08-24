package com.ajay.ecom_proj.controller;

import com.ajay.ecom_proj.DTO.AuthRequest;
import com.ajay.ecom_proj.DTO.AuthResponse;
import com.ajay.ecom_proj.DTO.ProductDTO;
import com.ajay.ecom_proj.DTO.RegistrationRequest;
import com.ajay.ecom_proj.mapper.ProductMapper;
import com.ajay.ecom_proj.model.Product;
import com.ajay.ecom_proj.model.Users;
import com.ajay.ecom_proj.repo.ProductRepo;
import com.ajay.ecom_proj.repo.UserRepo;
import com.ajay.ecom_proj.service.JWTService;
import com.ajay.ecom_proj.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @Autowired
    ProductRepo repo;
    @Autowired
    private JWTService jWTService;
    @Autowired
    private UserRepo userRepo;
    @RequestMapping("/")
    public String greeting() {
        return "Hello User you are now using the Ecommerce API Develpoped By Ajay Kumar Ray!!";
    }
    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
       return ResponseEntity.ok(service.getAllProducts());

    }
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProduct( @PathVariable int id) {

        ProductDTO product = service.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return  ResponseEntity.notFound().build();
        }
    }
// we are not sure what we are going to return we might return data or status
@PostMapping(
        value = "/product/add",
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
)
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> addProduct(
        @RequestPart("product") Object productPart,   // <-- can be JSON object or String
        @RequestPart("imageFile") MultipartFile imageFile,
        @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal
) {
    try {
        Product product;

        // Handle both cases (frontend sending JSON directly or as Blob)
        if (productPart instanceof String productJson) {
            ObjectMapper mapper = new ObjectMapper();
            product = mapper.readValue(productJson, Product.class);
        } else if (productPart instanceof Product p) {
            product = p;
        } else {
            return ResponseEntity.badRequest().body("Invalid product data");
        }

        // Get logged-in user
        String username = principal.getUsername();
        Users user = userRepo.findByUserName(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        // Attach logged-in user
        product.setUser(user);

        // Save product
        ProductDTO product1 = service.addProduct(product, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(product1);

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding product");
    }
}


    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable("id") int productId )
    {
        Product product = service.getProductEntity(productId);
        if(product == null||product.getImageData()==null)
            return ResponseEntity.notFound().build();
    String contentType = product.getImageType()!=null?product.getImageType():"application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + product.getImageName() + "\"")
                .body(product.getImageData());


    }
    @PutMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>  updateProduct(@PathVariable int id, @RequestPart Product product,@RequestPart MultipartFile imageFile) throws IOException {
        try{
            ProductDTO updatedProduct = service.updateProduct(id, product, imageFile);
            if(updatedProduct != null)
            {
                return ResponseEntity.ok(updatedProduct);
            }
            return   ResponseEntity.notFound().build();
        }
        catch(Exception e)
            {
            e.printStackTrace();
            return new  ResponseEntity<>( "Error updating Product ",HttpStatus.BAD_REQUEST);
            }
    }
    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable int id)
    {
        if(service.deleteProduct(id)){
            return ResponseEntity.ok("Product deleted successfully");
        }
        return  ResponseEntity.notFound().build();
    }
    @GetMapping("/product/search")

    public ResponseEntity<List<ProductDTO>> searchProduct(String keyword) {
        System.out.println("searching with..." + keyword);

        return ResponseEntity.ok(service.searchProducts(keyword));
    }

}
