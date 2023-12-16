package com.guraride.guraridebooking_system.mapper;


import com.guraride.guraridebooking_system.dto.RentalDto;
import com.guraride.guraridebooking_system.models.Rental;

public class RentalMapper {
    public static Rental mapToRental(RentalDto rentalDto){
       return Rental.builder()
                .rental_id(rentalDto.getRental_id())
                .startTime(rentalDto.getStartTime())
                .endTime(rentalDto.getEndTime())
                .isReturned(rentalDto.getIsReturned())
                .status(rentalDto.getStatus())
                .createdOn(rentalDto.getCreatedOn())
                .updatedOn(rentalDto.getUpdatedOn())
                .user(rentalDto.getUser())
                .bike(rentalDto.getBike())
                .build();
    }
    public static RentalDto mapToRentalDto(Rental rental){
       return RentalDto.builder()
                .rental_id(rental.getRental_id())
                .startTime(rental.getStartTime())
                .endTime(rental.getEndTime())
                .isReturned(rental.getIsReturned())
                .status(rental.getStatus())
                .createdOn(rental.getCreatedOn())
                .updatedOn(rental.getUpdatedOn())
                .user(rental.getUser())
                .bike(rental.getBike())
                .build();
    }
}
