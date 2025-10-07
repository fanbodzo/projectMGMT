package com.example.project_management_system.mapper;

import com.example.project_management_system.User;
import com.example.project_management_system.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//uzywam klasy dedykowanej do tego z biblioteka bo trzeba sir rzowijac
//to daje mi mozliwosc wstrzykiwania tego bo widzi spring to jako bean
@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if(user==null) return null;
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    //metoda do konwersji na seta potrzebujetego now
    public Set<UserDto> toDtoSet(Set<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<UserDto> toDtoList(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
