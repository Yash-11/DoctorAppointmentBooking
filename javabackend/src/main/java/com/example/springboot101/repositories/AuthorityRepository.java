package com.example.springboot101.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot101.models.Authority;


public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    
}
