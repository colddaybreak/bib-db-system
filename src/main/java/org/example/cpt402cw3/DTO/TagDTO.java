package org.example.cpt402cw3.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Tag DTO")
public class TagDTO {
    @Schema(description = "Primary key ID (not passed when creating)")
    private Long id;

    @Schema(description = "Tag name", example = "Machine Learning")
    private String name;
}