package com.ajay.ecom_proj.repo;

import com.ajay.ecom_proj.DTO.ProductDTO;
import com.ajay.ecom_proj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
//if the user wants to search by brand
    //JPQL query
@Query("SELECT DISTINCT p FROM Product p " +
        "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR p.category IN (SELECT DISTINCT p2.category FROM Product p2 " +
        "WHERE LOWER(p2.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR LOWER(p2.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR LOWER(p2.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))) ")
List<Product> searchProduct(@Param("keyword") String keyword);






}
