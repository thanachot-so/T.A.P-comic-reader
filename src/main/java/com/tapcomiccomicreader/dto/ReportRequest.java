package com.tapcomiccomicreader.dto;

import jakarta.validation.constraints.NotBlank;

public class ReportRequest {
    @NotBlank
    private String uuid;

    @NotBlank
    private String reason;

    private Integer commentId;

    private Integer replyId;

    public String commentType() {
        if (commentId != null) {
            return "COMMENT";
        } else if (replyId != null) {
            return "REPLY";
        }

        return null;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    @Override
    public String toString() {
        return "ReportRequest{" +
                "uuid='" + uuid + '\'' +
                ", reason='" + reason + '\'' +
                ", commentId=" + commentId +
                ", replyId=" + replyId +
                '}';
    }
}
