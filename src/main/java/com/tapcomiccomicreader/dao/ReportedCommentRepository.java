package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.Comment;
import com.tapcomiccomicreader.entity.ReplyComment;
import com.tapcomiccomicreader.entity.ReportedComment;
import com.tapcomiccomicreader.helperclass.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportedCommentRepository extends JpaRepository<ReportedComment, Integer> {

    @Query("SELECT r FROM ReportedComment r " +
            "WHERE r.status = :status")
    List<ReportedComment> findByStatus(@Param("status") ReportStatus status);

    @Query("SELECT r FROM ReportedComment r " +
            "WHERE r.comment = :comment")
    List<ReportedComment> findAllByComment(@Param("comment") Comment comment);

    @Query("SELECT r FROM ReportedComment r " +
            "WHERE r.reply = :reply")
    List<ReportedComment> findAllByReply(@Param("reply") ReplyComment reply);
}
