package com.example.ccidbackend.service;

import com.example.ccidbackend.dto.SignatureDTO;
import com.example.ccidbackend.entity.Signature;
import com.example.ccidbackend.repository.SignatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SignatureService {

    private final SignatureRepository signatureRepository;

    public SignatureDTO createSignature(Signature signature) {
        Signature saved = signatureRepository.save(signature);
        return mapToDTO(saved);
    }

    public SignatureDTO getSignatureById(Long id) {
        Signature signature = signatureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Signature not found"));
        return mapToDTO(signature);
    }

    public List<SignatureDTO> getAllSignatures() {
        return signatureRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public SignatureDTO updateSignature(Long id, Signature signature) {
        Signature existing = signatureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Signature not found"));

        existing.setName(signature.getName());

        Signature updated = signatureRepository.save(existing);
        return mapToDTO(updated);
    }

    public void deleteSignature(Long id) {
        Signature signature = signatureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Signature not found"));

        signatureRepository.delete(signature);
    }

    private SignatureDTO mapToDTO(Signature signature) {
        return SignatureDTO.builder()
                .id(signature.getId())
                .name(signature.getName())
                .build();
    }
}