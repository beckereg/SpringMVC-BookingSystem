package com.guraride.guraridebooking_system.repository;


import com.guraride.guraridebooking_system.models.Bike;
import com.guraride.guraridebooking_system.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("SELECT r FROM Rental r WHERE r.user.email LIKE CONCAT('%', :email, '%') AND r.status LIKE CONCAT('%', :status, '%')")
    List<Rental> findUserRentedBikes(String email, String status);
    List<Rental> findAllByStatus(String status);


} 