package com.tapcomiccomicreader.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReplyRequest {
    @NotNull
    private Integer commentId;

    @NotBlank
    private String uuid;

    @NotBlank
    private String text;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ReplyRequest{" +
                "commentId=" + commentId +
                ", uuid='" + uuid + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
