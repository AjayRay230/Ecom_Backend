package com.ajay.ecom_proj.controller;

import com.ajay.ecom_proj.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private CloudinaryService cloudinaryService;
    @GetMapping("/greeting")
    public String getGreeting(){
        return "Hello user you are inside the imagecontroller right now";
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, String> result = cloudinaryService.uploadFile(file);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Upload failed: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{publicId}")
    public ResponseEntity<?> deleteImage(@PathVariable String publicId) {
        try {
            cloudinaryService.deleteFile(publicId);
            return ResponseEntity.ok(Map.of("message", "Image deleted successfully", "public_id", publicId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Delete failed: " + e.getMessage()));
        }
    }
}
