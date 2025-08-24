package com.sewa.controller;

import com.sewa.dto.AuthResponse;
import com.sewa.dto.LoginRequest;
import com.sewa.dto.RegisterRequest;
import com.sewa.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            AuthResponse response = userService.register(registerRequest);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Registrasi berhasil");
            result.put("data", response);
            
            return ResponseEntity.ok(result);
            
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Terjadi kesalahan server");
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = userService.login(loginRequest);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Login berhasil");
            result.put("data", response);
            
            return ResponseEntity.ok(result);
            
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Terjadi kesalahan server");
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        try {
            boolean exists = userService.existsByEmail(email);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("exists", exists);
            result.put("message", exists ? "Email sudah terdaftar" : "Email tersedia");
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Terjadi kesalahan server");
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @GetMapping("/check-phone")
    public ResponseEntity<?> checkPhone(@RequestParam String phone) {
        try {
            boolean exists = userService.existsByPhone(phone);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("exists", exists);
            result.put("message", exists ? "Nomor telepon sudah terdaftar" : "Nomor telepon tersedia");
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Terjadi kesalahan server");
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}