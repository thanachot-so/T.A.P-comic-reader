package com.tapcomiccomicreader.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "read_history", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "comic_id"})
})
public class ReadHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    @JsonIgnore
    private Chapter readChapter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_id")
    @JsonIgnore
    private Comic comic;

    @Column(name = "current_page")
    private Integer pageNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_read_at")
    private LocalDateTime lastReadAt;

    @PrePersist
    @PreUpdate
    public void updateTime() {
        lastReadAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Chapter getReadChapter() {
        return readChapter;
    }

    public void setReadChapter(Chapter readChapter) {
        this.readChapter = readChapter;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public LocalDateTime getLastReadAt() {
        return lastReadAt;
    }

    public void setLastReadAt(LocalDateTime lastReadAt) {
        this.lastReadAt = lastReadAt;
    }

    @JsonProperty
    public String getUserUuid() {
        return user.getUuid();
    }

    @JsonProperty
    public String getComicUuid() {
        return comic.getUuid();
    }

    @JsonProperty
    public Integer getChapterId() {
        return readChapter.getId();
    }

    @Override
    public String toString() {
        return "ReadHistory{" +
                "id=" + id +
                ", user=" + user +
                ", readChapter=" + readChapter +
                ", comic=" + comic +
                ", pageNumber=" + pageNumber +
                ", lastReadAt=" + lastReadAt +
                '}';
    }
}
