package com.example.project_management_system.controller;

import com.example.project_management_system.User;
import com.example.project_management_system.dto.UserDto;
import com.example.project_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
//obsluga zadan webowych mapowanie etc
public class UserController {
    private final UserService userService;
    //wstyrzkniecie user service
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        try{
            UserDto user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    //to do ogarniecia bedzie w poznijesyzm kroku ajk bede ogarnaic kazade exception narazie response entityy dla kazdego
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try{
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }


}
