package com.ase.rrts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ase.rrts.model.Users;
import com.ase.rrts.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> getAllUser() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Users createUser(Users user) {
        return userRepository.save(user);
    }

    public Users updateUser(UUID id, Users user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public List<Users> getAllUsers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllUsers'");
    }
}
