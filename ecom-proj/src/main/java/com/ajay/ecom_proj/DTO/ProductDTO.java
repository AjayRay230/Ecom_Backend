package com.ajay.ecom_proj.DTO;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private String category;
    private boolean available;
    private LocalDate releaseDate;
    private String brand;
    private String imageUrl;
    private String createdBy;
}
