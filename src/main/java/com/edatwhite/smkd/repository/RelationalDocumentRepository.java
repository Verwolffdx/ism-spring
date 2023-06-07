package com.edatwhite.smkd.repository;

import com.edatwhite.smkd.entity.Users;
import com.edatwhite.smkd.entity.smkdocument.RelationalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RelationalDocumentRepository extends JpaRepository<RelationalDocument, String> {
    List<RelationalDocument> findByDoctype(String doctype);

    List<RelationalDocument> findByDoctypeSign(String sign);

//    Set<Users> findByFavoritesDocumentId(String doc_id);
}
