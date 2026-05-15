package com.groupxxx.bibdb.controller;

import com.groupxxx.bibdb.dto.ApiResponse;
import com.groupxxx.bibdb.dto.PublicationDto;
import com.groupxxx.bibdb.dto.PublicationWriteRequest;
import com.groupxxx.bibdb.service.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publications")
@RequiredArgsConstructor
@Tag(name = "Publications")
public class PublicationController {

    private final PublicationService publicationService;

    @GetMapping
    @Operation(summary = "Search publications by keyword and/or tag with paging")
    public ApiResponse<Page<PublicationDto>> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "year") String sortField,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        Page<PublicationDto> result =
                publicationService.search(q, tag, PageRequest.of(page, size, sort));
        return ApiResponse.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one publication")
    public ApiResponse<PublicationDto> get(@PathVariable Long id) {
        return ApiResponse.ok(publicationService.get(id));
    }

    @PostMapping
    @Operation(summary = "Create publication")
    public ApiResponse<PublicationDto> create(@Valid @RequestBody PublicationWriteRequest request) {
        return ApiResponse.ok(publicationService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update publication")
    public ApiResponse<PublicationDto> update(
            @PathVariable Long id, @Valid @RequestBody PublicationWriteRequest request) {
        return ApiResponse.ok(publicationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete publication")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        publicationService.delete(id);
        return ApiResponse.ok("deleted", null);
    }
}
