package com.example.usermanagement.repository;

import com.example.usermanagement.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section,Long> {
    Section findByCode(String code);

    Optional<List<Section>>  findAllByParentId(Long parentId);
}
