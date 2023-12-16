package com.guraride.guraridebooking_system.service;


import com.guraride.guraridebooking_system.dto.BikeDto;
import com.guraride.guraridebooking_system.dto.RentalDto;
import com.guraride.guraridebooking_system.models.Bike;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BikeService {
    List<BikeDto> findAllBike();
    Page<BikeDto> findPage(int pageNumber);
    Bike saveBike(BikeDto bikeDto);
    BikeDto findBikeById(Long bikeId);
    void updateBike(BikeDto bike);
    void deleteBike(Long bikeId);

    public List<Bike> findBikeByStatusAvailable();

    //    @Override
    //    public List<Bike> findBikeByStatusAvailable() {
    //        List<Bike> bike= bikeRepository.findBikesAvailable();
    //        return bike;
    //    }
    List<RentalDto> findAvailableBikes(String email, String status);

    List<Bike> findBikeByStatusAvailable(String email, String status);
}
