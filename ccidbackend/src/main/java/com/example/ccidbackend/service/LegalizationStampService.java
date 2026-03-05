package com.example.ccidbackend.service;

import com.example.ccidbackend.dto.LegalizationStampDTO;
import com.example.ccidbackend.entity.LegalizationStamp;
import com.example.ccidbackend.repository.DocumentRepository;
import com.example.ccidbackend.repository.LegalizationStampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LegalizationStampService {

    private final LegalizationStampRepository legalizationStampRepository;
    private final DocumentRepository documentRepository;

    public LegalizationStampDTO createLegalizationStamp(LegalizationStamp stamp) {
        LegalizationStamp savedStamp = legalizationStampRepository.save(stamp);
        return convertToDTO(savedStamp);
    }

    @Transactional(readOnly = true)
    public LegalizationStampDTO getLegalizationStampById(Long id) {
        LegalizationStamp stamp = legalizationStampRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Legalization stamp not found with id: " + id));
        return convertToDTO(stamp);
    }

   

    @Transactional(readOnly = true)
    public LegalizationStampDTO getLegalizationStampByStampNumber(String stampNumber) {
        LegalizationStamp stamp = legalizationStampRepository.findByStampNumber(stampNumber)
                .orElseThrow(() -> new RuntimeException("Legalization stamp not found with number: " + stampNumber));
        return convertToDTO(stamp);
    }

    @Transactional(readOnly = true)
    public List<LegalizationStampDTO> getAllLegalizationStamps() {
        return legalizationStampRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LegalizationStampDTO updateLegalizationStamp(Long id, LegalizationStamp stampDetails) {
        LegalizationStamp stamp = legalizationStampRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Legalization stamp not found with id: " + id));
        
        if (stampDetails.getCountry() != null) {
            stamp.setCountry(stampDetails.getCountry());
        }
        if (stampDetails.getSignedBy() != null) {
            stamp.setSignedBy(stampDetails.getSignedBy());
        }
        if (stampDetails.getSignedTitle() != null) {
            stamp.setSignedTitle(stampDetails.getSignedTitle());
        }
        if (stampDetails.getNotaryOffice() != null) {
            stamp.setNotaryOffice(stampDetails.getNotaryOffice());
        }
        if (stampDetails.getCertifiedPlace() != null) {
            stamp.setCertifiedPlace(stampDetails.getCertifiedPlace());
        }
        if (stampDetails.getCertifiedDate() != null) {
            stamp.setCertifiedDate(stampDetails.getCertifiedDate());
        }
        if (stampDetails.getConsularDepartment() != null) {
            stamp.setConsularDepartment(stampDetails.getConsularDepartment());
        }
        if (stampDetails.getQrCode() != null) {
            stamp.setQrCode(stampDetails.getQrCode());
        }
        if (stampDetails.getSignatureImage() != null) {
            stamp.setSignatureImage(stampDetails.getSignatureImage());
        }
        
        LegalizationStamp updatedStamp = legalizationStampRepository.save(stamp);
        return convertToDTO(updatedStamp);
    }

    public void deleteLegalizationStamp(Long id) {
        LegalizationStamp stamp = legalizationStampRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Legalization stamp not found with id: " + id));
        legalizationStampRepository.delete(stamp);
    }

    public LegalizationStampDTO convertToDTO(LegalizationStamp stamp) {
        return LegalizationStampDTO.builder()
                .id(stamp.getId())
             
                .country(stamp.getCountry())
                .signedBy(stamp.getSignedBy())
                .signedTitle(stamp.getSignedTitle())
                .notaryOffice(stamp.getNotaryOffice())
                .certifiedPlace(stamp.getCertifiedPlace())
                .certifiedDate(stamp.getCertifiedDate())
                .consularDepartment(stamp.getConsularDepartment())
                .stampNumber(stamp.getStampNumber())
                .qrCode(stamp.getQrCode())
                .signatureImage(stamp.getSignatureImage())
                .build();
    }
}
