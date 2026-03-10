package com.example.ccidbackend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ccidbackend.entity.Stamp;
import com.example.ccidbackend.repository.StampRepository;



@Service
public class StampService {

    @Autowired
    private StampRepository stampRepository;

    public Stamp findByCode(String code) {

        return stampRepository.findByCode(code);

    }
}