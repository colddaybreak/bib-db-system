package com.grouptxr.bibdb.service;

import com.grouptxr.bibdb.dto.PublicationDto;
import com.grouptxr.bibdb.dto.PublicationWriteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublicationService {

    Page<PublicationDto> search(String keyword, String tag, Pageable pageable);

    PublicationDto get(Long id);

    PublicationDto create(PublicationWriteRequest request);

    PublicationDto update(Long id, PublicationWriteRequest request);

    void delete(Long id);
}
