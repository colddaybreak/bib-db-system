package com.grouptxr.bibdb.bibdbsystem.model.repository;

import com.grouptxr.bibdb.bibdbsystem.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}