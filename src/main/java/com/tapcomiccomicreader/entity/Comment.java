package com.tapcomiccomicreader.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"followedComics", "followedFriends"})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_id")
    @JsonIgnore
    private Comic comic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    @JsonIgnore
    private Chapter chapter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id")
    @JsonIgnore
    private Page page;

    @OneToMany(mappedBy = "mainComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReplyComment> replies;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "is_edited")
    private boolean isEdited;

    @PrePersist
    public void onCreate() {
        this.createAt = LocalDateTime.now();
    }

    @JsonProperty("comicId")
    public Integer getComicId() {
        return comic != null ? comic.getId() : null;
    }

    @JsonProperty("chapterId")
    public Integer getChapterId() {
        return chapter != null ? chapter.getId() : null;
    }

    @JsonProperty("pageId")
    public Integer getPageId() {
        return page != null ? page.getId() : null;
    }

    public Comment() {
    }

    public Comment(User user, String text) {
        this();
        this.user = user;
        this.text = text;
        this.replies = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public List<ReplyComment> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyComment> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", comic=" + comic +
                ", chapter=" + chapter +
                ", page=" + page +
                ", replies=" + replies +
                ", createAt=" + createAt +
                ", isEdited=" + isEdited +
                '}';
    }
}

