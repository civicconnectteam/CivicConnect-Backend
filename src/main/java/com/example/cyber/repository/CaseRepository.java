package com.example.cyber.repository;

import com.example.cyber.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CaseRepository extends JpaRepository<Case, Long> {
    List<Case> findByUserId(Long userId);
}
