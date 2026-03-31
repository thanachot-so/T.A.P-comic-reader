package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.ReplyLike;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReplyLikeRepository extends GenericLikeableRepository<ReplyLike>{
    @Query("SELECT rl FROM ReplyLike rl " +
            "WHERE rl.user.id = :userId " +
            "AND rl.reply.id IN :targetId")
    List<ReplyLike> findByUserIdAndTargetIds(@Param("userId") int userId,
                                             @Param("targetId") List<Integer> targetId);

    @Query("SELECT rl FROM ReplyLike rl " +
            "WHERE rl.user.id = :userId " +
            "AND rl.reply.id = :targetId")
    Optional<ReplyLike> findByUserIdAndTargetId(@Param("userId") int userId,
                                                @Param("targetId") int targetId);

}
