package com.tapcomiccomicreader.dto;

import com.tapcomiccomicreader.entity.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class PageDTO {
    private String pageUrl;
    private int pageNumber;
    private int pageId;

    public PageDTO(Page page) {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString();

        this.pageUrl = url + page.getUrl();
        this.pageNumber = page.getCount();
        this.pageId = page.getId();
    }

    public PageDTO() {
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    @Override
    public String toString() {
        return "PageDTO{" +
                "pageUrl='" + pageUrl + '\'' +
                ", pageNumber=" + pageNumber +
                ", pageId=" + pageId +
                '}';
    }
}
