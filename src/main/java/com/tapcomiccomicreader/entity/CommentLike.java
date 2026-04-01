package com.tapcomiccomicreader.entity;

import com.tapcomiccomicreader.helperclass.Likeable;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "comment_likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "comment_id"})
})
public class CommentLike implements Likeable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_liked")
    private Boolean isLiked;

    public CommentLike() {
    }

    public CommentLike(Comment comment, User user, Boolean isLiked) {
        this.comment = comment;
        this.user = user;
        this.isLiked = isLiked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean isLiked() {
        return isLiked;
    }

    public void setLike(Boolean liked) {
        isLiked = liked;
    }

    @Override
    public Integer getTargetId() {
        return this.comment.getId();
    }

    @Override
    public String toString() {
        return "CommentLike{" +
                "id=" + id +
                ", comment=" + comment +
                ", user=" + user +
                ", isLiked=" + isLiked +
                '}';
    }
}
