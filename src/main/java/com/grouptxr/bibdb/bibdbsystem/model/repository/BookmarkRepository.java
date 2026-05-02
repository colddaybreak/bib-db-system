package com.grouptxr.bibdb.bibdbsystem.model.repository;

import com.grouptxr.bibdb.bibdbsystem.model.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUserId(Long userId);
    Optional<Bookmark> findByUserIdAndPublicationId(Long userId, Long pubId);
    void deleteByUserIdAndPublicationId(Long userId, Long pubId);
}