package com.edatwhite.smkd.repository;

import com.edatwhite.smkd.entity.RelationalDocumentDTO;
import com.edatwhite.smkd.entity.Users;
import com.edatwhite.smkd.entity.smkdocument.RelationalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface RelationalDocumentRepository extends JpaRepository<RelationalDocument, String> {
    List<RelationalDocument> findByDoctype(String doctype);

    @Query("SELECT r.document_id, r.document_code, r.document_name FROM RelationalDocument r")
    List<RelationalDocumentDTO> findAllWithoutFavorites();

    List<RelationalDocument> findByDoctypeSign(String sign);

//    Set<Users> findByFavoritesDocumentId(String doc_id);
}
