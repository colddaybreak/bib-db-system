package com.grouptxr.bibdb.model.repository;

import com.grouptxr.bibdb.model.entity.Bookmark;
import com.grouptxr.bibdb.model.entity.Publication;
import com.grouptxr.bibdb.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUserOrderByCreatedAtDesc(User user);

    Optional<Bookmark> findByUserAndPublication(User user, Publication publication);

    void deleteByUserAndPublication(User user, Publication publication);

    boolean existsByUserAndPublication(User user, Publication publication);
}
