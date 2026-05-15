package com.groupxxx.bibdb.service.impl;

import com.groupxxx.bibdb.dto.PublicationDto;
import com.groupxxx.bibdb.model.entity.Bookmark;
import com.groupxxx.bibdb.model.entity.Publication;
import com.groupxxx.bibdb.model.entity.User;
import com.groupxxx.bibdb.model.repository.BookmarkRepository;
import com.groupxxx.bibdb.model.repository.PublicationRepository;
import com.groupxxx.bibdb.service.BookmarkService;
import com.groupxxx.bibdb.service.UserService;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PublicationRepository publicationRepository;
    private final UserService userService;

    @Override
    @Transactional
    public void addBookmark(Long userId, Long publicationId) {
        User user = userService.getUserEntity(userId);
        Publication pub =
                publicationRepository
                        .findById(publicationId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publication not found"));
        if (bookmarkRepository.existsByUserAndPublication(user, pub)) {
            return;
        }
        Bookmark b = Bookmark.builder().user(user).publication(pub).createdAt(Instant.now()).build();
        bookmarkRepository.save(b);
    }

    @Override
    @Transactional
    public void removeBookmark(Long userId, Long publicationId) {
        User user = userService.getUserEntity(userId);
        Publication pub =
                publicationRepository
                        .findById(publicationId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publication not found"));
        bookmarkRepository.deleteByUserAndPublication(user, pub);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicationDto> listBookmarks(Long userId) {
        User user = userService.getUserEntity(userId);
        return bookmarkRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(Bookmark::getPublication)
                .map(
                        p -> {
                            p.getTags().size();
                            return PublicationDto.fromEntity(p);
                        })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isBookmarked(Long userId, Long publicationId) {
        User user = userService.getUserEntity(userId);
        Publication pub =
                publicationRepository
                        .findById(publicationId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publication not found"));
        return bookmarkRepository.existsByUserAndPublication(user, pub);
    }
}
