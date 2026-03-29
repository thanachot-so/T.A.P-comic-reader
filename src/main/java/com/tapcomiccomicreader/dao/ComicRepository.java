package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.Comic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ComicRepository  extends JpaRepository<Comic,Integer> {
    @Query("SELECT c FROM Comic c " +
            "WHERE c.uuid = :uuid")
    Optional<Comic> findComicByUuid(@Param("uuid") String uuid);

    @Query("SELECT c FROM Comic c " +
            "JOIN c.genres g " +
            "WHERE g.id = :genreId")
    List<Comic> findByGenreId(@Param("genreId") int genreId);

    @Query(value = "SELECT *, " +
            "(SELECT COUNT(*) FROM user_follows uf WHERE uf.comic_id = comic.id ) AS followerCount, " +
            "(SELECT COUNT(*) FROM comic_chapter cc WHERE cc.comic_id = comic.id) AS chapterCount " +
            "FROM comic WHERE title ILIKE '%' || :keyword || '%' OR title % :keyword " +
            "ORDER BY similarity(title, :keyword) DESC", nativeQuery = true)
    Page<Comic> searchByTitle(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT DISTINCT c FROM Comic c " +
            "JOIN c.genres g " +
            "WHERE g.id IN :genreIds")
    Page<Comic> searchByGenre(@Param("genreIds") Set<Integer> genreIds, Pageable pageable);
}
