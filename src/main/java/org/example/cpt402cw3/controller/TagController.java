package org.example.cpt402cw3.controller;


import org.example.cpt402cw3.common.ApiResponse;
import org.example.cpt402cw3.DTO.TagDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@Tag(name = "Tag API", description = "Tag CRUD operations")
public class TagController {

    @PostMapping
    @Operation(summary = "Create tag")
    public ApiResponse<TagDTO> create(@RequestBody TagDTO dto) {
        dto.setId(1L);
        return ApiResponse.success(dto);
    }

    @GetMapping
    @Operation(summary = "Get all tags")
    public ApiResponse<List<TagDTO>> list() {
        return ApiResponse.success(List.of(new TagDTO()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update tag")
    public ApiResponse<TagDTO> update(@PathVariable Long id, @RequestBody TagDTO dto) {
        dto.setId(id);
        return ApiResponse.success(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete tag")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        return ApiResponse.success(null);
    }
}