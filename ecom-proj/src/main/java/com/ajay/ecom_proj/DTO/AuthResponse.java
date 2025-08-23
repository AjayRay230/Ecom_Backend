package com.ajay.ecom_proj.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private Long userId;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
}
