package com.edatwhite.smkd.repository;

import com.edatwhite.smkd.entity.smkdocument.RelationalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationalDocumentRepository extends JpaRepository<RelationalDocument, String> {
    List<RelationalDocument> findByDoctype(String doctype);

    List<RelationalDocument> findByDoctypeSign(String sign);
}
