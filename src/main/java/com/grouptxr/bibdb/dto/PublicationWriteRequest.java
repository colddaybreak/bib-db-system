package com.grouptxr.bibdb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PublicationWriteRequest {

    @NotBlank private String title;

    @NotBlank private String authors;

    @NotNull private Integer year;

    private String abstractText;
    private String doi;
    private String url;
    private String bibtexRaw;
}
