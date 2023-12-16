package com.guraride.guraridebooking_system.service;


import com.guraride.guraridebooking_system.dto.RentalDto;
import com.guraride.guraridebooking_system.models.Rental;

import java.util.List;

public interface RentalService {
    List<RentalDto> findAllRental();

    Rental saveRental(RentalDto rentalDto);
    RentalDto findRentalById(Long RentalId);
    void updateRental(RentalDto rental);
    void deleteRental(Long rentalId);
    List<RentalDto> findUserRentedBikes(String email, String status);
    List<Rental> findAllByStatus(String status);
}
