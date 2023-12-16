package com.guraride.guraridebooking_system.implementation;

import com.guraride.guraridebooking_system.dto.BikeDto;
import com.guraride.guraridebooking_system.models.Bike;
import com.guraride.guraridebooking_system.repository.BikeRepository;
import com.guraride.guraridebooking_system.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.guraride.guraridebooking_system.mapper.BikeMapper.mapToBike;
import static com.guraride.guraridebooking_system.mapper.BikeMapper.mapToBikeDto;
import static com.guraride.guraridebooking_system.mapper.RentalMapper.mapToRentalDto;

@Service
public class BikeImp implements BikeService {

    private BikeRepository bikeRepository;

    @Autowired
    public BikeImp(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @Override
    public List<BikeDto> findAllBike() {
        List<Bike> bikes = bikeRepository.findAll();
        return bikes.stream().map((bike) -> mapToBikeDto(bike)).collect(Collectors.toList());
    }

    @Override
    @Cacheable("bikes")
    public Page<BikeDto> findPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        Page<Bike> bikePage = bikeRepository.findAll(pageable);
        return bikePage.map((bike) -> mapToBikeDto(bike));
    }

    @Override
    public Bike saveBike(BikeDto bikeDto) {

        Bike bike = mapToBike(bikeDto);
        return bikeRepository.save(bike);
    }

    @Override
    public BikeDto findBikeById(Long bikeId) {
        Bike bike = bikeRepository.findById(bikeId).get();
        return mapToBikeDto(bike);
    }

    @Override
    public void updateBike(BikeDto bikeDto) {
        Bike bike = mapToBike(bikeDto);
        bikeRepository.save(bike);
    }

    @Override
    public void deleteBike(Long bikeId) {
        bikeRepository.deleteById(bikeId);
    }

    @Override
    public List<Bike> findBikeByStatusAvailable(String email, String status) {
        List<Bike> bikes = bikeRepository.findAllByStatus(email, status);
        return bikes.stream().map(bike -> mapToRentalDto(bike)).collect(Collectors.toList());
    }

}

    //    @Override
//    public List<Bike> findBikeByStatusAvailable() {
//        List<Bike> bike= bikeRepository.findBikesAvailable();
//        return bike;
//    }



