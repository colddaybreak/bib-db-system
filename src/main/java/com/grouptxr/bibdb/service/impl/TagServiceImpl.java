package com.grouptxr.bibdb.service.impl;

import com.grouptxr.bibdb.dto.PublicationDto;
import com.grouptxr.bibdb.dto.TagDto;
import com.grouptxr.bibdb.model.entity.Publication;
import com.grouptxr.bibdb.model.entity.Tag;
import com.grouptxr.bibdb.model.repository.PublicationRepository;
import com.grouptxr.bibdb.model.repository.TagRepository;
import com.grouptxr.bibdb.service.TagService;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final PublicationRepository publicationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> listAll() {
        return tagRepository.findAll().stream()
                .map(t -> new TagDto(t.getId(), t.getName()))
                .sorted(Comparator.comparing(TagDto::name))
                .toList();
    }

    @Override
    @Transactional
    public PublicationDto addTagToPublication(Long publicationId, String tagName) {
        if (!StringUtils.hasText(tagName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag name required");
        }
        Publication p =
                publicationRepository
                        .findById(publicationId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publication not found"));
        String normalized = tagName.trim();
        Tag tag =
                tagRepository
                        .findByNameIgnoreCase(normalized)
                        .orElseGet(
                                () ->
                                        tagRepository.save(
                                                Tag.builder().name(normalized).build()));
        p.getTags().add(tag);
        tag.getPublications().add(p);
        publicationRepository.save(p);
        p.getTags().size();
        return PublicationDto.fromEntity(p);
    }

    @Override
    @Transactional
    public PublicationDto removeTagFromPublication(Long publicationId, String tagName) {
        Publication p =
                publicationRepository
                        .findById(publicationId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publication not found"));
        Tag tag =
                tagRepository
                        .findByNameIgnoreCase(tagName.trim())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found"));
        p.getTags().remove(tag);
        tag.getPublications().remove(p);
        publicationRepository.save(p);
        p.getTags().size();
        return PublicationDto.fromEntity(p);
    }
}
