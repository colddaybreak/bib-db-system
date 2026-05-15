package com.groupxxx.bibdb.model.repository;

import com.groupxxx.bibdb.model.entity.Bookmark;
import com.groupxxx.bibdb.model.entity.Publication;
import com.groupxxx.bibdb.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUserOrderByCreatedAtDesc(User user);

    Optional<Bookmark> findByUserAndPublication(User user, Publication publication);

    void deleteByUserAndPublication(User user, Publication publication);

    boolean existsByUserAndPublication(User user, Publication publication);
}
