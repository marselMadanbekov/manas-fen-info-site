package com.example.manasfen.configuration.security;

import com.example.manasfen.model.entyties.User;
import com.example.manasfen.reposiroties.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userStorage;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return build(userStorage.findUserByUsername(username).orElseThrow(()->new UsernameNotFoundException("Колдонуучу табылган жок!")));
    }

    private User build(User userData) {
        return User.builder()
                .username(userData.getUsername())
                .password(userData.getPassword())
                .role(userData.getRole())
                .active(userData.isActive())
                .build();
    }
}
