package com.sewa.repository;

import com.sewa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by email
     * @param email user email
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find user by phone number
     * @param phone user phone number
     * @return Optional<User>
     */
    Optional<User> findByPhone(String phone);
    
    /**
     * Check if email already exists
     * @param email user email
     * @return boolean
     */
    boolean existsByEmail(String email);
    
    /**
     * Check if phone number already exists
     * @param phone user phone number
     * @return boolean
     */
    boolean existsByPhone(String phone);
    
    /**
     * Find active user by email
     * @param email user email
     * @return Optional<User>
     */
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isActive = true")
    Optional<User> findActiveUserByEmail(@Param("email") String email);
    
    /**
     * Find user by email or phone
     * @param email user email
     * @param phone user phone
     * @return Optional<User>
     */
    @Query("SELECT u FROM User u WHERE u.email = :email OR u.phone = :phone")
    Optional<User> findByEmailOrPhone(@Param("email") String email, @Param("phone") String phone);
}