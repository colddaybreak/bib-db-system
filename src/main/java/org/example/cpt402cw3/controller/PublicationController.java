package org.example.cpt402cw3.controller;

import org.example.cpt402cw3.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.example.cpt402cw3.DTO.PublicationDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/publications")
@Tag(name = "Publication API", description = "Publication CRUD, Search")
public class PublicationController {

    @PostMapping
    @Operation(summary = "Create publication")
    public ApiResponse<PublicationDTO> create(@Valid @RequestBody PublicationDTO dto) {
        dto.setId(1L);
        return ApiResponse.success(dto);
    }

    @GetMapping
    @Operation(summary = "Query publication list by page")
    public ApiResponse<List<PublicationDTO>> list(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "Page must be >= 1") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "Size must be >= 1") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long tagId) {
        if (size > 100) {
            return ApiResponse.error(400, "Page size must not exceed 100");
        }
        return ApiResponse.success(List.of());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get publication details by id")
    public ApiResponse<PublicationDTO> getById(@PathVariable Long id) {
        if (id <= 0) {
            return ApiResponse.error(400, "Invalid publication ID");
        }
        PublicationDTO dto = new PublicationDTO();
        dto.setId(id);
        dto.setTitle("Fake Title");
        return ApiResponse.success(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update publication")
    public ApiResponse<PublicationDTO> update(@PathVariable Long id, @Valid @RequestBody PublicationDTO dto) {
        if (id <= 0) {
            return ApiResponse.error(400, "Invalid publication ID");
        }
        dto.setId(id);
        return ApiResponse.success(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete publication")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        if (id <= 0) {
            return ApiResponse.error(400, "Invalid publication ID");
        }
        return ApiResponse.success(null);
    }

    @PostMapping("/import")
    @Operation(summary = "Import publications from BibTeX file")
    public ApiResponse<String> importBibtex(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ApiResponse.error(400, "Uploaded file is empty");
        }
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.endsWith(".bib")) {
            return ApiResponse.error(400, "Only .bib files are accepted");
        }
        return ApiResponse.success("Imported from: " + filename);
    }
}