package org.example.cpt402cw3.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Publication DTO")
public class PublicationDTO {
    @Schema(description = "Primary key ID (not passed when creating)")
    private Long id;

    @Schema(description = "Title")
    private String title;

    @Schema(description = "Authors")
    private String authors;

    @Schema(description = "Publication year")
    private Integer year;

    @Schema(description = "Journal / Conference name")
    private String venue;

    @Schema(description = "Abstract")
    private String abstractText;

    @Schema(description = "BibTeX string")
    private String bibtex;

    @Schema(description = "Tag ID list")
    private List<Long> tagIds;
}