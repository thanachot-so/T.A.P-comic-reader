package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PageRepository extends JpaRepository<Page,Integer> {

    @Query("SELECT COALESCE(MAX(p.count), 0) FROM Page p WHERE p.chapter.id=:chapterId")
    Integer findLastPageNumber(@Param("chapterId") Integer chapterId);

    @Query("SELECT p.url FROM Page p " +
            "WHERE p.chapter.comic.uuid = :comicUuid " +
            "AND p.chapter.count = :chapterNum " +
            "AND p.count = :pageNum")
    Optional<String> findPageUrl(@Param("comicUuid") String comicUuid,
                                 @Param("chapterNum") int chapterNum,
                                 @Param("pageNum") int pageNum);

    @Query("SELECT p FROM  Page p " +
            "WHERE p.chapter.comic.id = :comicId " +
            "AND p.chapter.count = :chapterNum " +
            "AND p.count = :pageNum")
    Optional<Page> findPage(@Param("comicId") int comicId,
                            @Param("chapterNum") int chapterNum,
                            @Param("pageNum") int pageNum);

    @Query("SELECT p FROM Page p " +
            "WHERE p.chapter.count = :chapterNum " +
            "AND p.chapter.comic.uuid = :comicUuid " +
            "ORDER BY p.count asc")
    List<Page> findByComicUuidAndChapterNum(@Param("comicUuid") String comicUuid,
                                            @Param("chapterNum") int chapterNum);
}
