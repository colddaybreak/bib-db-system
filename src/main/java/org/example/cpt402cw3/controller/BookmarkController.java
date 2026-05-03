package org.example.cpt402cw3.controller;


import org.example.cpt402cw3.common.ApiResponse;
import org.example.cpt402cw3.DTO.PublicationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@Tag(name = "Bookmark API", description = "Publication bookmark / unbookmark")
public class BookmarkController {

    @PostMapping("/{publicationId}")
    @Operation(summary = "Bookmark or unbookmark publication")
    public ApiResponse<String> toggle(@PathVariable Long publicationId) {
        return ApiResponse.success("toggle ok");
    }

    @GetMapping
    @Operation(summary = "Get current user's bookmark list")
    public ApiResponse<List<PublicationDTO>> myList() {
        return ApiResponse.success(List.of());
    }
}