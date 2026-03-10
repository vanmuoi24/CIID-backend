package com.example.ccidbackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ccidbackend.entity.Stamp;


public interface StampRepository extends JpaRepository<Stamp, Long> {

    Stamp findByCode(String code);

}