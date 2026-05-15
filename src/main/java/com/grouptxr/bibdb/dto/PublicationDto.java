package com.groupxxx.bibdb.dto;

import com.groupxxx.bibdb.model.entity.Publication;
import java.util.List;
import java.util.stream.Collectors;

public record PublicationDto(
        Long id,
        String title,
        String authors,
        Integer year,
        String abstractText,
        String doi,
        String url,
        String bibtexRaw,
        List<String> tagNames) {

    public static PublicationDto fromEntity(Publication p) {
        List<String> names =
                p.getTags().stream().map(t -> t.getName()).sorted().collect(Collectors.toList());
        return new PublicationDto(
                p.getId(),
                p.getTitle(),
                p.getAuthors(),
                p.getYear(),
                p.getAbstractText(),
                p.getDoi(),
                p.getUrl(),
                p.getBibtexRaw(),
                names);
    }
}
