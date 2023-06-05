package com.edatwhite.smkd.repository;

import com.edatwhite.smkd.entity.Division;
import com.edatwhite.smkd.entity.smkdocument.DocType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocTypeRepository extends JpaRepository<DocType, Long> {

    Optional<DocType> findByName(String name);
}
