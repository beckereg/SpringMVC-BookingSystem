package com.guraride.guraridebooking_system.controller;

import com.guraride.guraridebooking_system.dto.BikeDto;
import com.guraride.guraridebooking_system.dto.RentalDto;
import com.guraride.guraridebooking_system.dto.UserDto;
import com.guraride.guraridebooking_system.models.Bike;
import com.guraride.guraridebooking_system.models.Rental;
import com.guraride.guraridebooking_system.models.User;
import com.guraride.guraridebooking_system.security.EndpointSessionAuthorize;
import com.guraride.guraridebooking_system.service.BikeService;
import com.guraride.guraridebooking_system.service.RentalService;
import com.guraride.guraridebooking_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final BikeService bikeService;
    private final RentalService rentalService;
    EndpointSessionAuthorize sessionAuthorize = new EndpointSessionAuthorize();

    @Autowired
    public UserController(
            UserService userService,
            BikeService bikeService,
            RentalService rentalService) {
        this.userService = userService;
        this.bikeService = bikeService;
        this.rentalService = rentalService;
    }

    @GetMapping("/user-available")
    public String userAvailable(HttpSession session, Model model) {
        if (sessionAuthorize.isRenter(session)) {
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            List<BikeDto> bike = bikeService.findAllBike();
            model.addAttribute("bikes", bike);
            return "dashboard/user-available";
        } else {
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }

    @GetMapping("/user-reserved")
    public String userReserved(HttpSession session, Model model) {
        if (sessionAuthorize.isRenter(session)) {
            Rental rental = new Rental();
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            //List<RentalDto> reservedBike = rentalService.findUserRentedBikes(retrievedEmail, "RESERVE");
            List<Rental> reservedBike = rentalService.findAllByStatus("RESERVE");
            System.out.printf("Returned: "+reservedBike.size());
            model.addAttribute("reservedBikes", reservedBike);
            return "dashboard/user-reserved";
        } else {
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }

    @GetMapping("/user-rented")
    public String userRented(HttpSession session, Model model) {
        if (sessionAuthorize.isRenter(session)) {
            Rental rental = new Rental();
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//            System.out.println(retrievedEmail);
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            List<Rental> rentedBike = rentalService.findAllByStatus("RENT");
            System.out.printf("Returned: "+rentedBike.size());
            model.addAttribute("rentedBikes", rentedBike);
            return "dashboard/user-rented";
        } else {
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @PostMapping("/user-rented")
    public String returnRentedBike(
            HttpSession session,
            @RequestParam("rentalId") Long rentalId,
            Model model) {
        if (sessionAuthorize.isRenter(session)) {
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            if (rentalId != null) {
                RentalDto rentedBike = rentalService.findRentalById(rentalId);
                if (rentedBike != null && rentedBike.getUser().getEmail().equals(retrievedEmail)) {
                    rentedBike.setIsReturned(true);
                    rentalService.saveRental(rentedBike);
                    return "dashboard/user-rented";
                }
            }
            return "redirect:/user-rented?fail=Invalid Rental ID";
        } else {
            return "redirect:/signin?fail=Unauthorized, Please First Signin!!";
        }
    }


    @GetMapping("/user-profile")
    public String userProfile(HttpSession session, Model model) {
        if (sessionAuthorize.isRenter(session)) {
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "dashboard/user-profile";
        } else {
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }

    @GetMapping("/user-reserve-rental")
    public String viewWorkerRegisterRental(HttpSession session, Model model) {
        if (sessionAuthorize.isRenter(session)) {
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            Bike bike = new Bike();
            model.addAttribute("bike", bike);
            return "user-reserve-rental";
        } else {
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";

        }
    }

    @PostMapping("/user-reserve-rental")
    public String workerRegisterRental(
            HttpSession session,
            @Valid @ModelAttribute("rentals") RentalDto rentalDto,
            @ModelAttribute("bike") Bike bike,
            BindingResult result,
            Model model) {

//        if (result.hasErrors()) {
//            model.addAttribute("rentals", rentalDto);
//            return "redirect:/user-reserve-rental?fail";
//        }
        if (result.hasErrors()) {
            model.addAttribute("rentals", rentalDto);
            return "user-reserve-rental";
        }
        String retrievedEmail = session.getAttribute("email").toString();
        model.addAttribute("retrievedEmail", retrievedEmail);
        Long retrievedID = Long.parseLong(session.getAttribute("userID").toString());
        User user = new User();
        user.setId(retrievedID);
        rentalDto.setIsReturned(false);
        rentalDto.setUser(user);
        rentalDto.setBike(bike);

        rentalService.saveRental(rentalDto);
        return "redirect:/user-reserved";
    }

    @GetMapping("user-info/delete/{id}")
    public String DeleteUser(
            Model model,
            @PathVariable("id") Long userId
    ) {
        userService.deleteUser(userId);
        return "redirect:/user-info";

    }
    @GetMapping("/user-info/edit/{id}")
    public String viewEditUserInfo(
            @PathVariable("id") Long userId,
            Model model
    ){
        UserDto user = userService.findUserById(userId);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(user.getId());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
        model.addAttribute("users", user);
        return "dashboard/user-info-edit";
    }
    @PostMapping("/user-info/edit/{id}")
    public String editUserInfo(@PathVariable("id") Long userId,
                               @Valid @ModelAttribute("user") UserDto users,
                               BindingResult result,
                               Model model,
                               HttpSession session){
        if(sessionAuthorize.isAdmin(session)){
            if(result.hasErrors()){
                model.addAttribute("user", users);
                return "dashboard/user-info?fail = Validation Error!!";
            }
            users.setId(userId);
            userService.updateUser(users);
            return "redirect:/user-info";
        }else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
}
