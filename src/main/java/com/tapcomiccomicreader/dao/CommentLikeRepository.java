package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.CommentLike;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends GenericLikeableRepository<CommentLike> {
    @Query("SELECT cl FROM CommentLike cl " +
            "WHERE cl.user.id = :userId " +
            "AND cl.comment.id = :targetId")
    Optional<CommentLike> findByUserIdAndTargetId(@Param("userId") int userId,
                                                  @Param("targetId") int targetId);

    @Query("SELECT cl FROM CommentLike cl " +
            "WHERE cl.user.id = :userId " +
            "AND cl.comment.id IN :targetId")
    List<CommentLike> findByUserIdAndTargetIds(@Param("userId") int userId,
                                               @Param("targetId") List<Integer> targetId);

    @Modifying
    @Query("DELETE FROM CommentLike cl WHERE cl.comment.id = :commentId")
    void deleteByCommentId(@Param("commentId") Integer commentId);
}
