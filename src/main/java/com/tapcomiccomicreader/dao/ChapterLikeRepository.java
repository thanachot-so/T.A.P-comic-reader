package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.Chapter;
import com.tapcomiccomicreader.entity.ChapterLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ChapterLikeRepository extends JpaRepository<ChapterLike, Integer> {
    @Query("SELECT cl FROM ChapterLike cl " +
            "WHERE cl.user.id = :userId " +
            "AND cl.chapter.id IN :chapterIds")
    List<ChapterLike> findByUserIdAndChapterIds(@Param("userId")int userId,
                                                @Param("chapterIds")List<Integer> chapterIds);

    @Query("SELECT cl FROM ChapterLike cl " +
            "WHERE cl.user.id = :userId " +
            "AND cl.chapter.id = :chapterId")
    Optional<ChapterLike> findByUserIdAndChapterId(@Param("userId")int userId,
                                                   @Param("chapterId")int chapterId);

    Set<Integer> chapter(Chapter chapter);
}
