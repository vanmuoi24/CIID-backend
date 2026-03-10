package com.example.ccidbackend.service;

import com.example.ccidbackend.dto.ApplicationDTO;
import com.example.ccidbackend.dto.DocumentDTO;
import com.example.ccidbackend.dto.LegalizationStampDTO;
import com.example.ccidbackend.dto.SignatureDTO;
import com.example.ccidbackend.entity.LegalizationStamp;
import com.example.ccidbackend.repository.DocumentRepository;
import com.example.ccidbackend.repository.LegalizationStampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;
import java.security.Signature;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LegalizationStampService {

    private final LegalizationStampRepository legalizationStampRepository;
    private final DocumentRepository documentRepository;
    private final CloudinaryService cloudinaryService;

    public LegalizationStampDTO createLegalizationStamp(LegalizationStamp stamp) {

        // tạo verification code tự động
        String verificationCode = UUID.randomUUID().toString().replace("-", "");

        stamp.setVerificationCode(verificationCode);

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
    public LegalizationStamp findByStampNumber(String stampNumber) {
        return legalizationStampRepository.findByStampNumber(stampNumber).orElse(null);
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

    private LegalizationStampDTO convertToDTO(LegalizationStamp stamp) {

        DocumentDTO documentDTO = null;
        ApplicationDTO applicationDTO = null;
        SignatureDTO signatureDTO = null;

        if (stamp.getSignature() != null) {

            signatureDTO = SignatureDTO.builder().id(stamp.getSignature().getId()).name(stamp.getSignature()
                    .getName()).build();

        }

        // map document
        if (stamp.getDocument() != null) {
            documentDTO = DocumentDTO.builder()
                    .id(stamp.getDocument().getId())
                    .cvType(stamp.getDocument().getCvType())
                    .documentTitle(stamp.getDocument().getDocumentTitle())
                    .documentType(stamp.getDocument().getDocumentType())
                    .holderName(stamp.getDocument().getHolderName())
                    .referenceNumber(stamp.getDocument().getReferenceNumber())
                    .issueDate(stamp.getDocument().getIssueDate())
                    .certifyingAuthority(stamp.getDocument().getCertifyingAuthority())
                    .certifyingSignatory(stamp.getDocument().getCertifyingSignatory())
                   
                    .build();
        }

        // map application
        if (stamp.getApplication() != null) {
            applicationDTO = ApplicationDTO.builder()
                    .id(stamp.getApplication().getId())
                    .receiptDate(stamp.getApplication().getReceiptDate())
                    .resultDate(stamp.getApplication().getResultDate())
                    .competentAuth(stamp.getApplication().getCompetentAuth())
                    .submissionMethod(stamp.getApplication().getSubmissionMethod())
                    .build();
        }

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
                .document(documentDTO)
                .application(applicationDTO)
                .image_url(stamp.getImageUrl())
                .verificationCode(stamp.getVerificationCode())
                .signature(signatureDTO)
                .build();
    }

    public LegalizationStampDTO getByCode(String code) {
        return convertToDTO(legalizationStampRepository.findByVerificationCode(code)
                .orElseThrow(() -> new RuntimeException("Not found")));
    }

    public LegalizationStampDTO updateImage(Long id, MultipartFile file) {

        LegalizationStamp stamp = legalizationStampRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy"));

        String imageUrl = cloudinaryService.uploadFile(file);

        stamp.setImageUrl(imageUrl);

        LegalizationStamp saved = legalizationStampRepository.save(stamp);

        return convertToDTO(saved);
    }
}
