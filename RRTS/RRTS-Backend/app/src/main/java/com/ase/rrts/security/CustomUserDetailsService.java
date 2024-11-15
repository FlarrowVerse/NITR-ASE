package com.ase.rrts.security;

import java.util.List;

import com.ase.rrts.model.Users; // Your User entity
import com.ase.rrts.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the database
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        
        // Ensure the user's roles or authorities are being set properly
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));

        // Convert your User entity to a UserDetails object
        return new User(
                user.getUsername(),
                user.getPassword(),
                authorities // Assuming you have roles or authorities
        );
    }
}
