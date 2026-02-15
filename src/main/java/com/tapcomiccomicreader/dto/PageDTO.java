package com.tapcomiccomicreader.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tapcomiccomicreader.entity.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class PageDTO {
    private String pageUrl;

    @JsonUnwrapped
    private Page page;

    public PageDTO(Page page) {
        this.page = page;

        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString();

        this.pageUrl = url + page.getUrl();
    }

    public PageDTO() {
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "PageDTO{" +
                "pageUrl='" + pageUrl + '\'' +
                ", page=" + page +
                '}';
    }
}
