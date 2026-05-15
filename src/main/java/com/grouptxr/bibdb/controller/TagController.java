package com.grouptxr.bibdb.controller;

import com.grouptxr.bibdb.dto.ApiResponse;
import com.grouptxr.bibdb.dto.PublicationDto;
import com.grouptxr.bibdb.dto.TagDto;
import com.grouptxr.bibdb.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Tags")
public class TagController {

    private final TagService tagService;

    @GetMapping("/tags")
    @Operation(summary = "List all tags")
    public ApiResponse<List<TagDto>> list() {
        return ApiResponse.ok(tagService.listAll());
    }

    @PostMapping("/publications/{id}/tags")
    @Operation(summary = "Attach a tag to a publication (creates tag if missing)")
    public ApiResponse<PublicationDto> addTag(
            @PathVariable("id") Long publicationId, @RequestParam String name) {
        return ApiResponse.ok(tagService.addTagToPublication(publicationId, name));
    }

    @DeleteMapping("/publications/{id}/tags")
    @Operation(summary = "Remove a tag from a publication")
    public ApiResponse<PublicationDto> removeTag(
            @PathVariable("id") Long publicationId, @RequestParam String name) {
        return ApiResponse.ok(tagService.removeTagFromPublication(publicationId, name));
    }
}
