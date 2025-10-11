package com.example.project_management_system.service;

import com.example.project_management_system.User;
import com.example.project_management_system.repository.UserRepository;
import com.example.project_management_system.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public JpaUserDetailsService(UserService userService, UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //znalezienie encji user w  bazie
        return userRepository.findByUsername(username)
                //zapakowanie jej wdo adaptera security
                .map(UserSecurity::new)
                //jak nie znaleziono w bazie to rzucamy wyjatek
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
