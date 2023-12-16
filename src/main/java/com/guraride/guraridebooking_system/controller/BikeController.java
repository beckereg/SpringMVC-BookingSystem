package com.guraride.guraridebooking_system.controller;


import com.guraride.guraridebooking_system.dto.BikeDto;
import com.guraride.guraridebooking_system.dto.RentalDto;
import com.guraride.guraridebooking_system.models.Bike;
import com.guraride.guraridebooking_system.models.User;
import com.guraride.guraridebooking_system.security.EndpointSessionAuthorize;
import com.guraride.guraridebooking_system.service.BikeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BikeController {
    private final BikeService bikeService;
    EndpointSessionAuthorize sessionAuthorize = new EndpointSessionAuthorize();
    @Autowired
    public BikeController(BikeService bikeService) {

        this.bikeService = bikeService;
    }
    @GetMapping("/register-bike")
    public String BikeRegisterView(HttpSession session, Model model) {
        if (sessionAuthorize.isAdmin(session)) {
            Bike bike = new Bike();
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            model.addAttribute("bike", bike);
            return "register-bike";
        } else {
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @PostMapping("/register-bike")
    public String BikeRegister(@Valid @ModelAttribute("bikes") BikeDto bikeDto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("bikes", bikeDto);
            return "redirect:/register-bike?fail= Validation Error";
        }
        bikeService.saveBike(bikeDto);
        return "redirect:/bike-info";
    }
    @GetMapping("/bike-info")
    public String getAllPagesBikeInfo(
            HttpSession session,
            Model model) {
        if (sessionAuthorize.isAdmin(session)) {
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return getOnePageBikeInfo(model, 1);
        } else {
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @GetMapping("/bike-info/page/{pageNumber}")
    public String getOnePageBikeInfo(
            Model model,
            @PathVariable("pageNumber") int currentPage) {
        Page<BikeDto> page = bikeService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<BikeDto> bikes = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("bikes", bikes);
        return "dashboard/bike-info";
    }
    @GetMapping("/bike-info/delete/{bikeId}")
    public String deleteBike(@PathVariable("bikeId")Long bikeId,
                             HttpSession session,
                             Model model) {
        if (sessionAuthorize.isAdmin(session)) {
            bikeService.deleteBike(bikeId);
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "redirect:/bike-info";
        } else {
             return "redirect:/signin?fail= Unauthorized, Please First Signin!!";

        }

    }
    @DeleteMapping("/worker-bike-info/delete/{bikeId}")
    public String workerDeleteBike(@PathVariable("bikeId")Long bikeId,
                                   HttpSession session,
                                   Model model) {
        if(sessionAuthorize.isWorker(session)){
            bikeService.deleteBike(bikeId);
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "redirect:/worker-bike-info";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";

        }
    }
    @GetMapping("/bike-info/edit/{bikeId}")
    public String editBike(@PathVariable("bikeId") Long bikeId, Model model, HttpSession session) {
        if(sessionAuthorize.isAdmin(session)){
            BikeDto bike = bikeService.findBikeById(bikeId);
            model.addAttribute("bike", bike);
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "bike-edit";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";

        }

    }
    @GetMapping("/worker-bike-info/edit/{bikeId}")
    public String WorkerEditBike(@PathVariable("bikeId") Long bikeId, Model model, HttpSession session) {
        if (sessionAuthorize.isWorker(session)) {
            BikeDto bike = bikeService.findBikeById(bikeId);
            model.addAttribute("bike", bike);
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "dashboard/worker-bike-edit";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";

        }

    }
//    @PutMapping("/bike-info/edit/{bikeId}")
//    public String updateBike(@PathVariable("bikeId") Long bikeId,
//                             @Valid @ModelAttribute("bike") BikeDto bikes,
//                             BindingResult result,
//                             Model model,
//                             HttpSession session) {
//        if(sessionAuthorize.isAdmin(session)){
//            if(result.hasErrors()) {
//                model.addAttribute("bike", bikes);
//                return "bike-edit?fail= Validation Error!!!";
//            }
//            bikes.setBikeId(bikeId);
//            bikeService.updateBike(bikes);
//            return "redirect:/bike-info";
//        }
//        else{
//
//            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
//        }
//    }


    @PostMapping("/worker-bike-info/edit/{bikeId}")
    public String workerUpdateBike(@PathVariable("bikeId") Long bikeId,
                                   @Valid @ModelAttribute("bike") BikeDto bikes,
                                   BindingResult result,
                                   Model model,
                                   HttpSession session) {
        System.out.println("In the edit method");
        if(sessionAuthorize.isWorker(session)){
//            if (result.hasErrors()) {
//                model.addAttribute("bike", bikes);
//                return "redirect:/worker-bike-info?fail=Validation Error!!!";
//            }
            System.out.println("In the edit method with params: " + bikeId+" and params: " + bikes.getRentPrice());
            bikes.setBikeId(bikeId);
            bikeService.saveBike(bikes);
            return "redirect:/worker-bike-info";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @GetMapping("worker-register-bike")
    public String workerBikeRegisterView(
            Model model,
            HttpSession session){
        if(sessionAuthorize.isWorker(session)){
            Bike bike = new Bike();
            model.addAttribute("bike", bike);
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "dashboard/worker-register-bike";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }

    @PostMapping("worker-register-bike")
    public String workerBikeRegister(@Valid @ModelAttribute("bikes") BikeDto bikeDto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("bikes", bikeDto);
            return "redirect:/worker-register-bike?fail = Validation Error";
        }
        bikeService.saveBike(bikeDto);
        return "redirect:/worker-bike-info";
    }


    @GetMapping("/user-reserve-rental/{bikeId}")
    public String viewWorkerRegisterRental(HttpSession session, Model model) {
        if (sessionAuthorize.isRenter(session)) {
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            Bike bike = new Bike();
            model.addAttribute("bike", bike);
            return "dashboard/user-reserve-rental";
        } else {
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";

        }
    }

    @PostMapping("/user-reserve-rental/{bikeId}")
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
            return "dashboard/user-reserved";
        }
        String retrievedEmail = session.getAttribute("email").toString();
        model.addAttribute("retrievedEmail", retrievedEmail);
        Long retrievedID = Long.parseLong(session.getAttribute("userID").toString());
        User user = new User();
        user.setId(retrievedID);
        rentalDto.setIsReturned(false);
        rentalDto.setUser(user);
        rentalDto.setBike(bike);

        //rentalService.saveRental(rentalDto);
        return "redirect:/user-reserved";
    }


}