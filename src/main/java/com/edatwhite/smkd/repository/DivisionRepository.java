package com.edatwhite.smkd.repository;

import com.edatwhite.smkd.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {
    @Override
    Optional<Division> findById(Long id);
}
