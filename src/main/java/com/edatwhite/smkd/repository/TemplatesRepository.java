package com.edatwhite.smkd.repository;

import com.edatwhite.smkd.entity.Templates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TemplatesRepository extends JpaRepository<Templates, Long> {
    List<Templates> findByDocumentId(String documentId);

    Optional<Templates> findByTemplateName(String templateName);
}
