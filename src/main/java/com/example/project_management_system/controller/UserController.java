package com.example.project_management_system.controller;

import com.example.project_management_system.User;
import com.example.project_management_system.dto.CreateUserRequestDto;
import com.example.project_management_system.dto.UserDto;
import com.example.project_management_system.mapper.UserMapper;
import com.example.project_management_system.service.UserService;
import jakarta.validation.Valid;
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
    private final UserMapper userMapper;

    //wstyrzkniecie user service
    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
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


//    @PostMapping
//    //@valid mowi ze zanim wykona metode to bierze obiekt i sprawdza go
//    //czy spelnia wszykie reguly zdefiniowane w jego klasie
//    //czyli zamin odpale mtode kaze wziac ten obiekt requestDto i dac go do validatora czyli bean validtor
//    //bean validator patrzy na klase CreateUserRequestDto i sprawdza adnotacje walidacyjne
//    //username ,haslo i email w tymprzypadku czy sie zgadza
//    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
//        //w zamianie na encje choidz o to ze
//        //requestDto to jest jakby na kartce napisane che takeigo itakiego usera
//        //dobra spoko walidator patrzy czy jest git i jak mowi ze git to leci dalej
//        //tutaj wchodzi kolejny posrednik ktory ma elemty zeby go stworzyc daltego tworyzmy encje
//        //ale dalczego encja? bo encja ma haslo do uzytkonika
//        //i zeby user trafil do bazy musi przejsc prze zrepoztytroum przeciez
//        //to dajemy userService stworz tego usera a serwis odowluje sie do repozytirum i zaspisuje tam uzytkonika
//        try{
//            User user = userMapper.toEntity(requestDto);
//            User createdUser = userService.createUser(user);
//            return new ResponseEntity<>(userMapper.toDto(createdUser), HttpStatus.CREATED);
//        }catch(IllegalArgumentException e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
//        }
//    }
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequestDto requestDto) {
        UserDto userDto = userService.createUser(requestDto);

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }


}
