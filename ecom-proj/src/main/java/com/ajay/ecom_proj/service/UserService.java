package com.ajay.ecom_proj.service;

import com.ajay.ecom_proj.model.Product;
import com.ajay.ecom_proj.model.Users;
import com.ajay.ecom_proj.repo.ProductRepo;
import com.ajay.ecom_proj.repo.UserRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

@Autowired
  private  ProductRepo productRepo;
@Autowired
 private JWTService jwtService;
@Autowired
private UserRepo userRepo;
@Autowired
private AuthenticationManager authenticationManager;
@Autowired
private PasswordEncoder passwordEncoder;
    public Product registerUser(Product user) {
        return productRepo.save(user);
    }

//    public Boolean validateUser(String username, String password) {
//        Product user = productRepo.findByUserName(username);
//        return user != null && user.getPassword().equals(password);
//
//    }

    public Optional<Product> getUserById(long id) {
        return productRepo.findById((int) id);
    }

    public Product addUserById(Product product) {
        return productRepo.save(product);
    }

    public Product registerUsers(Product user) {
        return productRepo.save(user);
    }

    public Users addUser(Users user) {
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setTimeStamp(new Date());
        return userRepo.save(user);
    }

    public boolean removeUser(int id) {
        return false;
    }

    public List<Users> getAllUsers() {
        return  userRepo.findAll();
    }
    public Users getUserByUserId(int userId) {
        return  userRepo.findUserByUserId(userId);

    }
@Transactional
    public boolean deleteUserByUsername(String username) {
        Users user = userRepo.findByUserName(username);

        if(user!=null)
        {
            userRepo.delete(user);
            return true;
        }
        return false;
    }
//    public String verify(Product user) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(user);
//        } else {
//            return "Fail";
//        }
//
//    }
}