package org.example.cpt402cw3.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Tag DTO")
public class TagDTO {
    @Schema(description = "Primary key ID (not passed when creating)")
    private Long id;

    @NotBlank(message = "Tag name cannot be blank")
    @Size(max = 100, message = "Tag name must be at most 100 characters")
    @Schema(description = "Tag name", example = "Machine Learning")
    private String name;
}