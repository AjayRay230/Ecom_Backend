package com.ajay.ecom_proj.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Autowired
    private ObjectMapper mapper ;
    @Bean
    public ObjectMapper objectMapper() {

        mapper.registerModule(new JavaTimeModule()); // enable LocalDate/LocalDateTime
        return mapper;
    }
}
