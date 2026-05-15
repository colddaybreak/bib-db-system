package com.groupxxx.bibdb.service;

import com.groupxxx.bibdb.dto.PublicationDto;
import com.groupxxx.bibdb.dto.TagDto;
import java.util.List;

public interface TagService {

    List<TagDto> listAll();

    PublicationDto addTagToPublication(Long publicationId, String tagName);

    PublicationDto removeTagFromPublication(Long publicationId, String tagName);
}
