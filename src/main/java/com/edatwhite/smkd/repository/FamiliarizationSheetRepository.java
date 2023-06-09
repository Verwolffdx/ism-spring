package com.edatwhite.smkd.repository;

import com.edatwhite.smkd.entity.FamiliarizationSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Set;

@Transactional
public interface FamiliarizationSheetRepository extends JpaRepository<FamiliarizationSheet, Long> {
    Set<FamiliarizationSheet> findByUserId(Long id);

    Set<FamiliarizationSheet> findByUserIdAndViewedFalse(Long id);

    @Query("SELECT DISTINCT documentId FROM FamiliarizationSheet WHERE viewed = false AND userId = ?1 ")
    Set<String> findDistinctByUserIdAndViewedFalse(Long id);

    Set<FamiliarizationSheet> findByUserIdAndDocumentId(Long userId, String documentId);

    @Query("SELECT DISTINCT viewed FROM FamiliarizationSheet WHERE userId = ?1 AND documentId = ?2")
    boolean isViewedByUserIdAndDocumentId(Long userId, String documentId);

    Set<FamiliarizationSheet> findByDocumentId(String documentId);

    Set<FamiliarizationSheet> findByDocumentIdOrderByFamDivision(String documentId);

    @Query("SELECT DISTINCT documentId FROM FamiliarizationSheet")
    Set<String> getDistinctDocumentId();

    @Query("SELECT DISTINCT famDivision FROM FamiliarizationSheet WHERE documentId = ?1 ORDER BY famDivision")
    Set<Integer> getDistinctFamDivisionByDocumentIdOrderByFamDivision(String documentId);

    boolean existsFamiliarizationSheetByUserIdAndDocumentId(Long userId, String documentId);

    long deleteDocumentIdByDocumentId(String documentId);

    int countViewedByDocumentIdAndViewedTrue(String documentId);
    int countViewedByDocumentIdAndViewedFalse(String documentId);

    int countViewedByDocumentIdAndFamDivisionAndViewedTrue(String documentId, Long famDivision);
    int countViewedByDocumentIdAndFamDivisionAndViewedFalse(String documentId, Long famDivisio);

}
