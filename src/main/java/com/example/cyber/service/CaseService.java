package com.example.cyber.service;

import com.example.cyber.model.Case;
import com.example.cyber.repository.CaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseService {
    private final CaseRepository caseRepo;

    public CaseService(CaseRepository caseRepo) {
        this.caseRepo = caseRepo;
    }

    public Case addCase(Case c) {
        return caseRepo.save(c);
    }

    public List<Case> getUserCases(Long userId) {
        return caseRepo.findByUserId(userId);
    }

    public Case updateCaseStatus(Long caseId, String newStatus) {
        Case existingCase = caseRepo.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found"));
        existingCase.setStatus(newStatus);
        return caseRepo.save(existingCase);
    }

    public Case updateCase(Long caseId, String newStatus, String adminReview) {
        Case existingCase = caseRepo.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found"));
        if (newStatus != null && !newStatus.isBlank()) existingCase.setStatus(newStatus.trim());
        if (adminReview != null) existingCase.setAdminReview(adminReview);
        return caseRepo.save(existingCase);
    }

    public List<Case> getAllCases() {
        return caseRepo.findAll();
    }
    
    public void deleteCase(Long caseId) {
        Case c = caseRepo.findById(caseId)
            .orElseThrow(() -> new RuntimeException("Case not found"));
        caseRepo.delete(c);
    }

}
