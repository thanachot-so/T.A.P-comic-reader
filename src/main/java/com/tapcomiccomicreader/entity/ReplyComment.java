package com.tapcomiccomicreader.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reply_comments")
public class ReplyComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"followedComics", "followedFriends"})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_comment_id", nullable = false)
    @JsonIgnore
    private Comment mainComment;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @PrePersist
    public void onCreate() {
        createAt = LocalDateTime.now();
    }

    public ReplyComment() {
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

    public Comment getMainComment() {
        return mainComment;
    }

    public void setMainComment(Comment mainComment) {
        this.mainComment = mainComment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @JsonProperty
    public Integer getMainCommentId() {
        return mainComment.getId();
    }

    @Override
    public String toString() {
        return "ReplyComment{" +
                "id=" + id +
                ", user=" + user +
                ", mainComment=" + mainComment +
                ", text='" + text + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
