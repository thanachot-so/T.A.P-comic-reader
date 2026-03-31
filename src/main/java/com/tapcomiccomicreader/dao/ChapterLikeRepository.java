package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.ChapterLike;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterLikeRepository extends GenericLikeableRepository<ChapterLike> {
    @Query("SELECT cl FROM ChapterLike cl " +
            "WHERE cl.user.id = :userId " +
            "AND cl.chapter.id = :targetId")
    Optional<ChapterLike> findByUserIdAndTargetId(@Param("userId") int userId,
                                                  @Param("targetId") int targetId);

    @Query("SELECT cl FROM ChapterLike cl " +
            "WHERE cl.user.id = :userId " +
            "AND cl.chapter.id IN :targetId")
    List<ChapterLike> findByUserIdAndTargetIds(@Param("userId") int userId,
                                              @Param("targetId") List<Integer> targetId);
}
