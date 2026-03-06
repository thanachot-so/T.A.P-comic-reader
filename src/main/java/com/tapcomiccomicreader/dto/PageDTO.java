package com.tapcomiccomicreader.dto;

import com.tapcomiccomicreader.entity.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class PageDTO {
    private String pageUrl;
    private int pageNumber;

    public PageDTO(Page page) {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString();

        this.pageUrl = url + page.getUrl();
        this.pageNumber = page.getCount();
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

    @Override
    public String toString() {
        return "PageDTO{" +
                "pageUrl='" + pageUrl + '\'' +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
