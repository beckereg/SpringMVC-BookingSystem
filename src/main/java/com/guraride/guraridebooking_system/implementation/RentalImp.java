package com.guraride.guraridebooking_system.implementation;


import com.guraride.guraridebooking_system.dto.RentalDto;
import com.guraride.guraridebooking_system.models.Rental;
import com.guraride.guraridebooking_system.repository.RentalRepository;
import com.guraride.guraridebooking_system.service.RentalService;
import jakarta.persistence.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.guraride.guraridebooking_system.mapper.RentalMapper.mapToRental;
import static com.guraride.guraridebooking_system.mapper.RentalMapper.mapToRentalDto;

@Service
public class RentalImp implements RentalService {
    private RentalRepository rentalRepository;
    //private PasswordEncoder passwordEncoder;
    @Autowired
    public RentalImp(RentalRepository rentalRepository)
    {

        this.rentalRepository = rentalRepository;
    }
    @Override
    public List<RentalDto> findAllRental() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream().map((rental) -> mapToRentalDto(rental)).collect(Collectors.toList());
    }

    @Override
   // @Cacheable("rentals")
    public Rental saveRental(RentalDto rentalDto) {
        Rental rental = mapToRental(rentalDto);
        return rentalRepository.save(rental);
    }
    @Override
    public RentalDto findRentalById(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId).get();
        return mapToRentalDto(rental);
    }

    @Override
    public void updateRental(RentalDto rentalDto) {
        Rental rental = mapToRental(rentalDto);
        rentalRepository.save(rental);
    }

    @Override
    public void deleteRental(Long rentalId)
    {
        rentalRepository.deleteById(rentalId);
    }

    @Override
    public List<RentalDto> findUserRentedBikes(String email, String status) {
        List<Rental> rentals = rentalRepository.findUserRentedBikes(email, status);
        return rentals.stream().map(rental -> mapToRentalDto(rental)).collect(Collectors.toList());
    }

    @Override
    public List<Rental> findAllByStatus(String status) {
        return rentalRepository.findAllByStatus(status);
    }


}
