package com.ajay.ecom_proj.mapper;





import com.ajay.ecom_proj.DTO.ProductDTO;
import com.ajay.ecom_proj.model.Product;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
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
        dto.setReleaseDate(product.getReleaseDate()); // direct mapping (LocalDate)
        dto.setBrand(product.getBrand());

        // If you expose images via endpoint `/product/{id}/image`
        dto.setImageUrl("/api/product/" + product.getId() + "/image");
        if(product.getUser()!=null)
        {
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
        product.setReleaseDate(dto.getReleaseDate()); // direct mapping (LocalDate)
        product.setBrand(dto.getBrand());

        // ⚠️ image is not set here — handled via MultipartFile in controller
        return product;
    }
}
