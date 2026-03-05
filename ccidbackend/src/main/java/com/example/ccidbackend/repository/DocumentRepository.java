package com.example.ccidbackend.repository;

import com.example.ccidbackend.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    // Lấy document theo stamp id
    Optional<Document> findByLegalizationStamp_Id(Long stampId);

}