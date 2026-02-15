package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter,Integer> {
    @Query("SELECT c FROM Chapter c " +
            "WHERE c.comic.id = :comicId " +
            "ORDER BY c.count ASC")
    List<Chapter> findByComicId(@Param("comicId") Integer comicId);

    @Query("SELECT COALESCE(MAX(c.count), 0) FROM Chapter c WHERE c.comic.id=:comicId")
    Integer findLastChapterNumber(@Param("comicId") Integer comicId);

}
