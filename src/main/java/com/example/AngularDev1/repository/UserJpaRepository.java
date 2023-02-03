package com.example.AngularDev1.repository;

import com.example.AngularDev1.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserDTO, Long>{
    UserDTO findByName(String name);
}
