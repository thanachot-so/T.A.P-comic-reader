package com.tapcomiccomicreader.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReadHistoryRequest {
    @NotBlank
    private String userUuid;

    @NotNull
    private Integer chapterId;

    @NotNull
    private Integer pageNumber;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "ReadHistoryRequest{" +
                "userUuid='" + userUuid + '\'' +
                ", chapterId=" + chapterId +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
