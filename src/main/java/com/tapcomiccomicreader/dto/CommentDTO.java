package com.tapcomiccomicreader.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tapcomiccomicreader.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record CommentDTO(
        int id,
        String text,
        int comicId,
        int chapterId,
        int pageId,
        int likeCount,
        int dislikeCount,
        boolean edited,
        Boolean currentUserVote,
        UserDTO user,
        List<ReplyCommentDTO> replies,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createAt
) {
        public CommentDTO(Comment comment, Boolean currentUserVote, List<ReplyCommentDTO> replyCommentDTOS) {
                this(
                        comment.getId(),
                        comment.getText(),
                        comment.getComicId() != null ? comment.getComicId() : 0,
                        comment.getChapterId() != null ? comment.getChapterId() : 0,
                        comment.getPageId() != null ? comment.getPageId() : 0,
                        comment.getLikeCount(),
                        comment.getDislikeCount(),
                        comment.isEdited(),
                        currentUserVote,
                        new UserDTO(comment.getUser()),
                        replyCommentDTOS,
                        comment.getCreateAt()
                );
        }
}
