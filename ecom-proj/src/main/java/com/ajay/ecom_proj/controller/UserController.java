package com.ajay.ecom_proj.controller;

import com.ajay.ecom_proj.DTO.AuthRequest;
import com.ajay.ecom_proj.DTO.AuthResponse;
import com.ajay.ecom_proj.DTO.RegistrationRequest;
import com.ajay.ecom_proj.model.Product;
import com.ajay.ecom_proj.model.Users;
import com.ajay.ecom_proj.repo.UserRepo;
import com.ajay.ecom_proj.service.JWTService;
import com.ajay.ecom_proj.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    PasswordEncoder encoder;
    @GetMapping("/greeting")
    public String greeting(){
        return "Hello User you are inside the user controller api of ecom project";
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request)
    {
        if(userRepo.findByUserName(request.getUserName())!=null)
        {
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("The user already exists");
        }
        Users user = new Users();
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole("USER");
        userRepo.save(user);
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(
                token,
                (long) user.getUserId(),
                user.getRole(),

                user.getLastName(),
                user.getFirstName(),
                user.getEmail()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest request) {
        Users user = userRepo.findByUserName(request.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        if (!user.getEmail().equalsIgnoreCase(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(
                token,
                (long) user.getUserId(),
                user.getRole(),
                user.getLastName(),
                user.getFirstName(),
                user.getEmail()
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addUser")
    public ResponseEntity<Users> addUser(@RequestBody @Valid Users user) {
        Users savedUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteOwnAccount(Authentication authentication) {
        String username = authentication.getName(); // Extract username from token/session

        boolean deleted = userService.deleteUserByUsername(username);

        if (deleted) {
            return ResponseEntity.ok("Your account has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User account not found.");
        }
    }
    @GetMapping("/AllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Users>> getAllUsers() {
       List<Users> users = userService.getAllUsers();
       return ResponseEntity.status(HttpStatus.OK).body(users);
    }
    @GetMapping("/id/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId) {
        Users user = userService.getUserByUserId(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user);
    }
}
