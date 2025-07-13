package com.ajay.ecom_proj.service;

import com.ajay.ecom_proj.model.Product;
import com.ajay.ecom_proj.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class ProductService {
    @Autowired
    ProductRepo repo;
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(  int id) {
        return repo.findById(id).orElse(null);

    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }


    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
       product.setImageData(imageFile.getBytes());
       product.setImageName(imageFile.getOriginalFilename());
       product.setImageType(imageFile.getContentType());
        return  repo.save(product);

    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProduct(keyword);

    }
}

