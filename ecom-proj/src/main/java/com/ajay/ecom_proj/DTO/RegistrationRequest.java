package com.ajay.ecom_proj.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String firstName;
    private String lastName;

    private String userName;
    private String password;
    private String email;
    private BigDecimal amount;
    private String role;
}

