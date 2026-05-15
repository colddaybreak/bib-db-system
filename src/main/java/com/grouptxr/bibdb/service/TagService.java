package com.grouptxr.bibdb.service;

import com.grouptxr.bibdb.dto.PublicationDto;
import com.grouptxr.bibdb.dto.TagDto;
import java.util.List;

public interface TagService {

    List<TagDto> listAll();

    PublicationDto addTagToPublication(Long publicationId, String tagName);

    PublicationDto removeTagFromPublication(Long publicationId, String tagName);
}
