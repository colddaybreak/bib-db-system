package com.grouptxr.bibdb.bibdbsystem.model.repository;

import com.grouptxr.bibdb.bibdbsystem.model.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    Page<Publication> findByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(
            String titleKeyword, String authorKeyword, Pageable pageable);
}