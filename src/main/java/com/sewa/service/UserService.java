package com.sewa.service;

import com.sewa.dto.AuthResponse;
import com.sewa.dto.LoginRequest;
import com.sewa.dto.RegisterRequest;
import com.sewa.entity.User;
import com.sewa.repository.UserRepository;
import com.sewa.util.JwtUtil;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public UserService(UserRepository userRepository, 
                      PasswordEncoder passwordEncoder,
                      JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    

    
    public AuthResponse register(RegisterRequest request) {
        // Validasi password confirmation
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Password dan konfirmasi password tidak sama");
        }
        
        // Cek apakah email sudah terdaftar
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email sudah terdaftar");
        }
        
        // Cek apakah nomor telepon sudah terdaftar
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Nomor telepon sudah terdaftar");
        }
        
        // Buat user baru
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Simpan user ke database
        User savedUser = userRepository.save(user);
        
        // Generate JWT token
        String token = jwtUtil.generateToken(savedUser);
        
        return new AuthResponse(token, savedUser);
    }
    
    public AuthResponse login(LoginRequest request) {
        // Load user details
        User user = userRepository.findActiveUserByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email atau password salah"));
        
        // Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email atau password salah");
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user);
        
        return new AuthResponse(token, user);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
}