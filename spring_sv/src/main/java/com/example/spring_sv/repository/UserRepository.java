package com.example.spring_sv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_sv.model.User;


public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
     
}