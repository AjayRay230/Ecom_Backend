package com.ajay.ecom_proj.repo;

import com.ajay.ecom_proj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
//if the user wants to search by brand
    //JPQL query
    @Query("SELECT p from Product p where "  +
            "LOWER(p.name) LIKE LOWER(CONCAT( '%' ,:keyword ,'%')) OR " +
      "LOWER(p.description) LIKE LOWER(CONCAT( '%' ,:keyword ,'%')) OR "
       + "LOWER(p.brand) LIKE LOWER(CONCAT( '%' ,:keyword ,'%')) OR "
    +  "LOWER(p.category) LIKE LOWER(CONCAT( '%' ,:keyword ,'%'))  ")
    public List<Product> searchProduct(String keyword);
}
