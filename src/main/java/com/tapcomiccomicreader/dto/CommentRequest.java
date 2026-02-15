package com.tapcomiccomicreader.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentRequest {
    @NotBlank(message = "user UUID is required")
    private String userUuid;

    @NotBlank(message = "user comment is required")
    private String text;

    @NotNull(message = "comic id is required")
    private Integer comicId;

    private Integer chapterId;
    private Integer pageId;

    public boolean isPageComment() {
        return pageId != null;
    }

    public boolean isChapterComment() {
        return chapterId != null && pageId == null;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getComicId() {
        return comicId;
    }

    public void setComicId(Integer comicId) {
        this.comicId = comicId;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }
}
