package com.edatwhite.smkd.repository;

import com.edatwhite.smkd.entity.Division;
import com.edatwhite.smkd.entity.Templates;
import com.edatwhite.smkd.entity.Users;
import com.edatwhite.smkd.entity.smkdocument.RelationalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findByFio(String fio);

    Boolean existsByUsername(String username);

    Set<Users> findByDivisions(Division division);

    Set<RelationalDocument> findUsersByFavorites(String documentId);

    Set<Users> findByFavoritesTemplate(Long templateId);
}
