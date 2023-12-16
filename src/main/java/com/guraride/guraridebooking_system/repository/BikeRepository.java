package com.guraride.guraridebooking_system.repository;


import com.guraride.guraridebooking_system.models.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BikeRepository extends JpaRepository<Bike, Long> {

    @Query("SELECT u from Bike u WHERE u.status LIKE CONCAT('%', :status, '%')")
    List<Bike> findBikesByStatus(String status);
//    @Query("from Bike where status='available'")
//    List<Bike>  findBikesAvailable();
    @Query("SELECT u from Bike u WHERE u.status LIKE CONCAT('%', :status, '%')")
    List<Bike> findAllByStatus(String status, String s);
}
