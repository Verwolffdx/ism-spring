package com.edatwhite.smkd.repository;

import com.edatwhite.smkd.entity.smkdocument.RelationalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationalDocumentRepository extends JpaRepository<RelationalDocument, String> {
}
