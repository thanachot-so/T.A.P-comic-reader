package com.tapcomiccomicreader.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chapter_likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "chapter_id"})
})
public class ChapterLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @Column(name = "is_liked", nullable = false)
    private boolean isLiked;

    public ChapterLike() {
    }

    public ChapterLike(User user, Chapter chapter, boolean isLiked) {
        this.user = user;
        this.chapter = chapter;
        this.isLiked = isLiked;
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

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    @Override
    public String toString() {
        return "ChapterLike{" +
                "id=" + id +
                ", user=" + user +
                ", chapter=" + chapter +
                ", isLiked=" + isLiked +
                '}';
    }
}
