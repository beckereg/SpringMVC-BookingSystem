package com.guraride.guraridebooking_system.repository;


import com.guraride.guraridebooking_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query("SELECT u from User u WHERE u.status LIKE CONCAT('%', :status, '%')")
    List<User> findRentersByStatus(String status);
    @Query("SELECT u from User u WHERE u.status LIKE CONCAT('%', :status, '%')")
    List<User> findWorkersByStatus(String status);



}
