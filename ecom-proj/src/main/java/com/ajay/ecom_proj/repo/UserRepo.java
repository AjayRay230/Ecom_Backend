package com.ajay.ecom_proj.repo;

import com.ajay.ecom_proj.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users,Integer> {

    Users findByUserName(String username);

    Users findUserByUserId(int userId);


}
