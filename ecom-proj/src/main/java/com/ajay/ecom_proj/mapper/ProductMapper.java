package com.ajay.ecom_proj.mapper;

import com.ajay.ecom_proj.DTO.ProductDTO;
import com.ajay.ecom_proj.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setCategory(product.getCategory());
        dto.setAvailable(product.isAvailable());
        dto.setReleaseDate(product.getReleaseDate());
        dto.setBrand(product.getBrand());

        // ✅ map Cloudinary URL directly
        dto.setImageUrl(product.getImageUrl());

        if (product.getUser() != null) {
            dto.setCreatedBy(product.getUser().getUserName());
        }
        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());
        product.setAvailable(dto.isAvailable());
        product.setReleaseDate(dto.getReleaseDate());
        product.setBrand(dto.getBrand());

        // ✅ map back Cloudinary URL
        product.setImageUrl(dto.getImageUrl());

        return product;
    }
}
