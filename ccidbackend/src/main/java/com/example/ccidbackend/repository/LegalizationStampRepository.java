package com.example.ccidbackend.repository;

import com.example.ccidbackend.entity.LegalizationStamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LegalizationStampRepository extends JpaRepository<LegalizationStamp, Long> {

    Optional<LegalizationStamp> findByStampNumber(String stampNumber);

    // Nếu muốn tìm stamp theo document id (1-1)
    Optional<LegalizationStamp> findByDocument_Id(Long documentId);

    // Nếu muốn tìm stamp theo application id
    Optional<LegalizationStamp> findByApplication_Id(Long applicationId);


    Optional<LegalizationStamp> findByVerificationCode(String verificationCode);
}