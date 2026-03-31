package com.tapcomiccomicreader.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tapcomiccomicreader.entity.ReplyComment;

import java.time.LocalDateTime;

public record ReplyCommentDTO(
        int id,
        String text,
        int mainCommentId,
        int likeCount,
        int dislikeCount,
        UserDTO user,
        Boolean currentUserVote,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createAt
) {
        public ReplyCommentDTO(ReplyComment reply, Boolean currentUserVote) {
                this(
                        reply.getId(),
                        reply.getText(),
                        reply.getMainCommentId(),
                        reply.getLikeCount(),
                        reply.getDislikeCount(),
                        new UserDTO(reply.getUser()),
                        currentUserVote,
                        reply.getCreateAt()
                );
        }
}
