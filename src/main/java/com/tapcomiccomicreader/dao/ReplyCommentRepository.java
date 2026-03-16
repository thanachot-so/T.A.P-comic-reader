package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Integer> {
    @Query("SELECT r FROM ReplyComment r WHERE r.mainComment.id = :parentId")
    List<ReplyComment> findByParentId(int parentId);
}
