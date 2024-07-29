package com.example.springboot101.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot101.models.Authority;
import com.example.springboot101.repositories.AuthorityRepository;


@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority save(Authority authority){
        return authorityRepository.save(authority);

    }

    public Optional<Authority> findById (Long id) {
        return authorityRepository.findById(id);
    }

}
