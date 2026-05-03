package org.example.cpt402cw3.controller;


import org.example.cpt402cw3.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.cpt402cw3.DTO.PublicationDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publications")
@Tag(name = "Publication API", description = "Publication CRUD, Search")
public class PublicationController {

    @PostMapping
    @Operation(summary = "Create publication")
    public ApiResponse<PublicationDTO> create(@RequestBody PublicationDTO dto) {
        dto.setId(1L);
        return ApiResponse.success(dto);
    }

    @GetMapping
    @Operation(summary = "Query publication list by page")
    public ApiResponse<List<PublicationDTO>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long tagId) {
        // Mock data: return empty list
        return ApiResponse.success(List.of());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get publication details by id")
    public ApiResponse<PublicationDTO> getById(@PathVariable Long id) {
        PublicationDTO dto = new PublicationDTO();
        dto.setId(id);
        dto.setTitle("Fake Title");
        return ApiResponse.success(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update publication")
    public ApiResponse<PublicationDTO> update(@PathVariable Long id, @RequestBody PublicationDTO dto) {
        dto.setId(id);
        return ApiResponse.success(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete publication")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        return ApiResponse.success(null);
    }
}