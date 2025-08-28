package com.ajay.ecom_proj.service;

import com.ajay.ecom_proj.DTO.ProductDTO;
import com.ajay.ecom_proj.mapper.ProductMapper;
import com.ajay.ecom_proj.model.Product;
import com.ajay.ecom_proj.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepo repo;
    @Autowired
    ProductMapper mapper;
    public List<ProductDTO> getAllProducts() {
//        System.out.println("Get all products :"+ repo.findAll());
        return repo.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(  int id) {
        return repo.findById(id)
                .map(ProductMapper::toDTO)
                .orElse(null);

    }
    public ProductDTO addProduct(Product product) {
        Product saved = repo.save(product);
        return mapper.toDTO(saved);
    }



    public ProductDTO updateProduct(int id, ProductDTO dto) {
        Optional<Product> existing = repo.findById(id);

        if (existing.isPresent()) {
            Product product = existing.get();
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setQuantity(dto.getQuantity());

            // update image only if provided
            if (dto.getImageUrl() != null && !dto.getImageUrl().isBlank()) {
                product.setImageUrl(dto.getImageUrl());
            }

            Product updated = repo.save(product);
            return mapper.toDTO(updated);
        }
        return null;
    }

    public List<ProductDTO> searchProducts(String keyword) {
       return repo.searchProduct(keyword).stream().map(ProductMapper::toDTO).collect(Collectors.toList());

    }
    public Product getProductEntity(int id) {
        return repo.findById(id).orElse(null);
    }
    public boolean deleteProduct(int id) {
        if (repo.existsById(id)) {
           repo.deleteById(id);
            return true; // ✅ deleted successfully
        }
        return false; // ❌ not found
    }
}

