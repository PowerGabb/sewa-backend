package com.sewa.dto;

import com.sewa.entity.User;

public class AuthResponse {
    
    private String token;
    private String type = "Bearer";
    private UserResponse user;
    
    // Constructors
    public AuthResponse() {}
    
    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = new UserResponse(user);
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public UserResponse getUser() {
        return user;
    }
    
    public void setUser(UserResponse user) {
        this.user = user;
    }
    
    // Inner class for user response
    public static class UserResponse {
        private Long id;
        private String fullName;
        private String email;
        private String phone;
        
        public UserResponse() {}
        
        public UserResponse(User user) {
            this.id = user.getId();
            this.fullName = user.getFullName();
            this.email = user.getEmail();
            this.phone = user.getPhone();
        }
        
        // Getters and Setters
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getFullName() {
            return fullName;
        }
        
        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getPhone() {
            return phone;
        }
        
        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}