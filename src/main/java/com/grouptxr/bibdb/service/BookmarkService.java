package com.grouptxr.bibdb.service;

import com.grouptxr.bibdb.dto.PublicationDto;
import java.util.List;

public interface BookmarkService {

    void addBookmark(Long userId, Long publicationId);

    void removeBookmark(Long userId, Long publicationId);

    List<PublicationDto> listBookmarks(Long userId);

    boolean isBookmarked(Long userId, Long publicationId);
}
