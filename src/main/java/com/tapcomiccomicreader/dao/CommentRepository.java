package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.Comic;
import com.tapcomiccomicreader.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c " +
            "JOIN FETCH c.user " +
            "WHERE c.comic.id = :comicId")
    List<Comment> findByComicId(@Param("comicId") int comicId);

    @Query("SELECT c FROM Comment c " +
            "JOIN FETCH c.user " +
            "WHERE c.chapter.id = :chapterId")
    List<Comment> findByChapterId(@Param("chapterId") int chapterId);

    @Query("SELECT c FROM Comment c " +
            "JOIN FETCH c.user " +
            "WHERE c.page.id = :pageId")
    List<Comment> findByPageId(@Param("pageId") int pageId);

    int comic(Comic comic);
}