package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.Comic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ComicRepository  extends JpaRepository<Comic,Integer> {
    @Query("SELECT c FROM Comic c " +
            "WHERE c.uuid = :uuid")
    Optional<Comic> findComicByUuid(@Param("uuid") String uuid);

    @Query(value = "SELECT *, " +
            "(SELECT COUNT(*) FROM user_follows uf WHERE uf.comic_id = id ) AS followerCount, " +
            "(SELECT COUNT(*) FROM comic_chapter cc WHERE cc.comic_id = id) AS chapterCount " +
            "FROM comic WHERE title ILIKE '%' || :keyword || '%' OR title % :keyword " +
            "ORDER BY similarity(title, :keyword) DESC", nativeQuery = true)
    Page<Comic> searchByTitle(@Param("keyword") String keyword, Pageable pageable);
}
