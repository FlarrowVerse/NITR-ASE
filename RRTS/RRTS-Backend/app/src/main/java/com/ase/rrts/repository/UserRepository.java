package com.ase.rrts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ase.rrts.model.Area;
import com.ase.rrts.model.Users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    
    Optional<Users> findByUsername(String username);
    
    Optional<Users> findByEmail(String email);
    
    Optional<Users> findByPhone(String phone);

    Optional<List<Users>> findByArea(Area area);
}
