package com.tapcomiccomicreader.dto;

public class SearchUserRequest {
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "SearchUserRequest{" +
                "keyword='" + keyword + '\'' +
                '}';
    }
}
