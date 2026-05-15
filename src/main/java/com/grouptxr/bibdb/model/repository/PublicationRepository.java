package com.groupxxx.bibdb.model.repository;

import com.groupxxx.bibdb.model.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PublicationRepository extends JpaRepository<Publication, Long> {

    @Query(
            """
            select distinct p from Publication p
            left join p.tags t
            where lower(p.title) like lower(concat('%', :q, '%'))
               or lower(p.authors) like lower(concat('%', :q, '%'))
            """)
    Page<Publication> searchByKeyword(@Param("q") String q, Pageable pageable);

    @Query(
            """
            select distinct p from Publication p join p.tags t
            where lower(t.name) = lower(:tag)
            """)
    Page<Publication> findByTagName(@Param("tag") String tag, Pageable pageable);

    @Query(
            """
            select distinct p from Publication p join p.tags t
            where (lower(p.title) like lower(concat('%', :q, '%'))
                or lower(p.authors) like lower(concat('%', :q, '%')))
              and lower(t.name) = lower(:tag)
            """)
    Page<Publication> searchByKeywordAndTag(
            @Param("q") String q, @Param("tag") String tag, Pageable pageable);
}
