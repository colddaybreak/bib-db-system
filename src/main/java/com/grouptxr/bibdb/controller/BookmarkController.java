package com.groupxxx.bibdb.controller;

import com.groupxxx.bibdb.dto.ApiResponse;
import com.groupxxx.bibdb.dto.PublicationDto;
import com.groupxxx.bibdb.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/{userId}/bookmarks")
@RequiredArgsConstructor
@Tag(name = "Bookmarks", description = "Pass userId explicitly until JWT is added")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/{publicationId}")
    @Operation(summary = "Add bookmark")
    public ApiResponse<Void> add(@PathVariable Long userId, @PathVariable Long publicationId) {
        bookmarkService.addBookmark(userId, publicationId);
        return ApiResponse.ok("saved", null);
    }

    @DeleteMapping("/{publicationId}")
    @Operation(summary = "Remove bookmark")
    public ApiResponse<Void> remove(@PathVariable Long userId, @PathVariable Long publicationId) {
        bookmarkService.removeBookmark(userId, publicationId);
        return ApiResponse.ok("removed", null);
    }

    @GetMapping
    @Operation(summary = "List bookmarked publications")
    public ApiResponse<List<PublicationDto>> list(@PathVariable Long userId) {
        return ApiResponse.ok(bookmarkService.listBookmarks(userId));
    }

    @GetMapping("/{publicationId}/exists")
    @Operation(summary = "Check if publication is bookmarked")
    public ApiResponse<Boolean> exists(@PathVariable Long userId, @PathVariable Long publicationId) {
        return ApiResponse.ok(bookmarkService.isBookmarked(userId, publicationId));
    }
}
