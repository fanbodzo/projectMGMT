package com.example.project_management_system.service;

import com.example.project_management_system.User;
import com.example.project_management_system.dto.UserDto;
import com.example.project_management_system.mapper.UserMapper;
import com.example.project_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//serwis sluzy do ustalania zasad warstwa 2
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    //wstyrzkniecei userRepo
    @Autowired
    public UserService(UserRepository userRepository , UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    //uzywanie metod z jpa findAll()
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);

    }

    // metoda na uzywanie bez Dto
//    public User getUserById(Long id) {
//        return userRepository.findById(id).get();
//    }
    //metoda z uzywaniem dto i recznym mapowaniem

//    public UserDto getUserById(Long id) {
//        //Dto jest to jakby kontner do transportowania czyli
//        //musimy przekazac mu tego usera zwyklego wiec
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("this user does not exist"));
//
//        // teraz nie daje return ten user bo bym przkazal cale dane o nim a w dto mam dane ktore chce przekazac czyli nie wszystko
//        //wiec musze zmapowac dane recznie , pozniej uzyje automatu
//        // ale najpierw trzeba stworzyc obiekt dto
//
//        UserDto userDto = new UserDto();
//        //nie zapomniec dac lomboka w UserDto.java
//        userDto.setId(user.getId());
//        userDto.setUsername(user.getUsername());
//        userDto.setEmail(user.getEmail());
//
//        return userDto;
//    }
    // metoda z mapperem
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User id not found"));
        return userMapper.toDto(user);
    }

    public User createUser(User user) {
        if(!userRepository.existsByUsername(user.getUsername())) {
            if(!userRepository.existsByEmail(user.getEmail())) {
                user.setPassword("hashed_"+user.getPassword());
                return userRepository.save(user);
            }
            return null;
        }
        return null;
    }

}
