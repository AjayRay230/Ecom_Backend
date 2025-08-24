package com.ajay.ecom_proj.service;

import com.ajay.ecom_proj.DTO.ProductDTO;
import com.ajay.ecom_proj.mapper.ProductMapper;
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

    public ProductDTO addProduct(Product product, MultipartFile imageFile) throws IOException {
        if( imageFile!=null && !imageFile.isEmpty() ) {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageData(imageFile.getBytes());
        }
        else
        {
            product.setImageData(null);
        }
        Product saved  = repo.save(product);
        return mapper.toDTO(saved);
    }


    public ProductDTO updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
      Product existingProduct = repo.findById(id).orElse(null);
      if(existingProduct==null) {
          return null;
      }
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setAvailable(product.isAvailable());
        existingProduct.setReleaseDate(product.getReleaseDate());
        existingProduct.setBrand(product.getBrand());
        if(imageFile!=null && !imageFile.isEmpty())
        {
            existingProduct.setImageData(imageFile.getBytes());
            existingProduct.setImageType(imageFile.getContentType());
            existingProduct.setImageName(imageFile.getOriginalFilename());
        }
       Product saved = repo.save(existingProduct);
        return mapper.toDTO(saved);
    }

    public boolean deleteProduct(int id) {
       if(repo.existsById(id)) {
           repo.deleteById(id);
           return true;

       }
       return false;
    }

    public List<ProductDTO> searchProducts(String keyword) {
       return repo.searchProduct(keyword).stream().map(ProductMapper::toDTO).collect(Collectors.toList());

    }
    public Product getProductEntity(int id) {
        return repo.findById(id).orElse(null);
    }

}

