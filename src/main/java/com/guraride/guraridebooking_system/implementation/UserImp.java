package com.guraride.guraridebooking_system.implementation;

import com.guraride.guraridebooking_system.controller.SendEmail;
import com.guraride.guraridebooking_system.dto.UserDto;
import com.guraride.guraridebooking_system.models.User;
import com.guraride.guraridebooking_system.repository.UserRepository;
import com.guraride.guraridebooking_system.service.EmailService;
import com.guraride.guraridebooking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.guraride.guraridebooking_system.mapper.UserMapper.mapToUser;
import static com.guraride.guraridebooking_system.mapper.UserMapper.mapToUserDto;

@Service
public class UserImp implements UserService {
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;


    @Autowired
    public UserImp(UserRepository userRepository, SendEmail sendEmail)
    {
        this.userRepository = userRepository;
//        this.sendEmail = sendEmail;
    }
    @Override
    public List<UserDto> findAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }
    @Override
    @Cacheable("users")
    public Page<UserDto> findPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map((user) -> mapToUserDto(user));
    }

    @Override
    @Cacheable("adminFindRenters")
    public List<UserDto> findRentersByStatus(String status) {
        List<User> users = userRepository.findRentersByStatus(status);
        return users.stream().map(user -> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    @Cacheable("adminFindWorkers")
    public List<UserDto> findWorkersByStatus(String status) {
        List<User> users = userRepository.findWorkersByStatus(status);
        return users.stream().map(user -> mapToUserDto(user)).collect(Collectors.toList());
    }
    @Override
    public User saveUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setNationalId(userDto.getNationalId());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAddress(userDto.getAddress());
        user.setStatus(userDto.getStatus());
        user.setPassword((userDto.getPassword()));
       // user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User newUser=userRepository.save(user);
        if(newUser != null){
            emailService.sendEmail(user);
        }
        return newUser;
    }

    @Override
    @Cacheable("loginAdminFindEmail")
    public User findByEmail(String email) {
        return  userRepository.findByEmail(email);
    }
    @Override
    @Cacheable("loginAdminFindEmailPassword")
    public User findByEmailAndPassword(String email, String password){
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }

        if(!password.equals(user.getPassword())){
            return null;
        }
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            return null;
//        }

        return user;
    }
    @Override
    public UserDto findUserById(Long userId) {
        User user = userRepository.findById(userId).get();
        return mapToUserDto(user);
    }


    @Override
    public void updateUser(UserDto userDto) {
    User user = mapToUser(userDto);
    userRepository.save(user);
    }
    @Override
    public void deleteUser(Long userId) {
         userRepository.deleteById(userId);
    }

}
