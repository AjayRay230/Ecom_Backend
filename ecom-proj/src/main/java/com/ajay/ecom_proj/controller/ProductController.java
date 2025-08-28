package com.ajay.ecom_proj.controller;


import com.ajay.ecom_proj.DTO.ProductDTO;
import com.ajay.ecom_proj.mapper.ProductMapper;
import com.ajay.ecom_proj.model.Product;
import com.ajay.ecom_proj.model.Users;
import com.ajay.ecom_proj.repo.ProductRepo;
import com.ajay.ecom_proj.repo.UserRepo;
import com.ajay.ecom_proj.service.CloudinaryService;
import com.ajay.ecom_proj.service.JWTService;
import com.ajay.ecom_proj.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @Autowired
    private ProductMapper mapper;
    @Autowired
    private CloudinaryService cloudinaryService;
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

    @PostMapping("/product/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProduct(
            @RequestBody @Valid ProductDTO productDTO,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal
    ) {
        try {
            String username = principal.getUsername();
            Users user = userRepo.findByUserName(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            if (productDTO.getImageUrl() == null || productDTO.getImageUrl().isBlank()) {
                return ResponseEntity.badRequest().body("Image URL is required");
            }

            Product product = ProductMapper.toEntity(productDTO);
            product.setUser(user);

            ProductDTO saved = service.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding product: " + e.getMessage());
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<String> getImageByProductId(@PathVariable("id") int productId) {
        Product product = service.getProductEntity(productId);
        if (product == null || product.getImageUrl() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product.getImageUrl());
    }

    @GetMapping("/product/{id}/image/redirect")
    public ResponseEntity<Void> redirectToImage(@PathVariable int id) {
        Product product = service.getProductEntity(id);
        if (product == null || product.getImageUrl() == null || product.getImageUrl().isBlank()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(product.getImageUrl()))
                .build();
    }

    @PutMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(
            @PathVariable int id,
            @RequestBody ProductDTO productDTO
    ) {
        try {
            ProductDTO updatedProduct = service.updateProduct(id, productDTO);
            return (updatedProduct != null)
                    ? ResponseEntity.ok(updatedProduct)
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error updating product: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        return service.deleteProduct(id)
                ? ResponseEntity.ok("Product deleted successfully")
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestParam String keyword) {
        System.out.println("Searching with: " + keyword);
        return ResponseEntity.ok(service.searchProducts(keyword));
    }

    @PostMapping("/product/upload-image")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String url = cloudinaryService.uploadFile(file).toString();

            // return structured JSON response instead of plain string
            Map<String, String> response = new HashMap<>();
            response.put("url", url);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Image upload failed: " + e.getMessage());
        }
    }


}
