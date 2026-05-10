package org.example.cpt402cw3.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Publication DTO")
public class PublicationDTO {
    @Schema(description = "Primary key ID (not passed when creating)")
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 500, message = "Title must be at most 500 characters")
    @Schema(description = "Title", example = "Deep Learning for NLP")
    private String title;

    @NotBlank(message = "Authors cannot be blank")
    @Schema(description = "Authors", example = "John Smith, Jane Doe")
    private String authors;

    @NotNull(message = "Publication year cannot be null")
    @Schema(description = "Publication year", example = "2024")
    private Integer year;

    @Schema(description = "Journal / Conference name", example = "IEEE")
    private String venue;

    @Schema(description = "Abstract")
    private String abstractText;

    @Schema(description = "BibTeX string")
    private String bibtex;

    @Schema(description = "Tag ID list")
    private List<Long> tagIds;
}