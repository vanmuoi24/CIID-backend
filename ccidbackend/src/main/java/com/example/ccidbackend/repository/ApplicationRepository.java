package com.example.ccidbackend.repository;

import com.example.ccidbackend.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
  
    List<Application> findByStatus(String status);
}
