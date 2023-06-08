package com.edatwhite.smkd.repository;

import com.edatwhite.smkd.entity.FamiliarizationSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Transactional
public interface FamiliarizationSheetRepository extends JpaRepository<FamiliarizationSheet, Long> {
    Set<FamiliarizationSheet> findByUserId(Long id);

    Set<FamiliarizationSheet> findByUserIdAndViewedFalse(Long id);

    FamiliarizationSheet findByUserIdAndDocumentId(Long userId, String documentId);

    Set<FamiliarizationSheet> findFamDivisionByDocumentId(String documentId);

    boolean existsFamiliarizationSheetByUserIdAndDocumentId(Long userId, String documentId);

    long deleteDocumentIdByDocumentId(String documentId);


}
