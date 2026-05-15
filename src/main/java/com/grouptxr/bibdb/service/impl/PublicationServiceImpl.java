package com.groupxxx.bibdb.service.impl;

import com.groupxxx.bibdb.dto.PublicationDto;
import com.groupxxx.bibdb.dto.PublicationWriteRequest;
import com.groupxxx.bibdb.model.entity.Publication;
import com.groupxxx.bibdb.model.repository.PublicationRepository;
import com.groupxxx.bibdb.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<PublicationDto> search(String keyword, String tag, Pageable pageable) {
        String q = StringUtils.hasText(keyword) ? keyword.trim() : "";
        String t = StringUtils.hasText(tag) ? tag.trim() : "";

        Page<Publication> page;
        if (!StringUtils.hasText(q) && !StringUtils.hasText(t)) {
            page = publicationRepository.findAll(pageable);
        } else if (!StringUtils.hasText(q)) {
            page = publicationRepository.findByTagName(t, pageable);
        } else if (!StringUtils.hasText(t)) {
            page = publicationRepository.searchByKeyword(q, pageable);
        } else {
            page = publicationRepository.searchByKeywordAndTag(q, t, pageable);
        }
        return page.map(PublicationDto::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public PublicationDto get(Long id) {
        Publication p = find(id);
        p.getTags().size(); // touch lazy collection
        return PublicationDto.fromEntity(p);
    }

    @Override
    @Transactional
    public PublicationDto create(PublicationWriteRequest request) {
        Publication p = mapNew(request);
        publicationRepository.save(p);
        return PublicationDto.fromEntity(p);
    }

    @Override
    @Transactional
    public PublicationDto update(Long id, PublicationWriteRequest request) {
        Publication p = find(id);
        p.setTitle(request.getTitle());
        p.setAuthors(request.getAuthors());
        p.setYear(request.getYear());
        p.setAbstractText(request.getAbstractText());
        p.setDoi(request.getDoi());
        p.setUrl(request.getUrl());
        p.setBibtexRaw(request.getBibtexRaw());
        publicationRepository.save(p);
        p.getTags().size();
        return PublicationDto.fromEntity(p);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Publication p = find(id);
        publicationRepository.delete(p);
    }

    private Publication find(Long id) {
        return publicationRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publication not found"));
    }

    private static Publication mapNew(PublicationWriteRequest r) {
        return Publication.builder()
                .title(r.getTitle())
                .authors(r.getAuthors())
                .year(r.getYear())
                .abstractText(r.getAbstractText())
                .doi(r.getDoi())
                .url(r.getUrl())
                .bibtexRaw(r.getBibtexRaw())
                .build();
    }
}
