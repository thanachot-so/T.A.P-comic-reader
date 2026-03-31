package com.tapcomiccomicreader.entity;

import com.tapcomiccomicreader.helperclass.Likeable;
import jakarta.persistence.*;

@Entity
@Table(name = "reply_likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "reply_id"})
})
public class ReplyLike implements Likeable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id", nullable = false)
    private ReplyComment reply;

    @Column(name = "is_liked")
    private Boolean isLiked;

    public ReplyLike() {
    }

    public ReplyLike(User user, ReplyComment reply, Boolean isLiked) {
        this.user = user;
        this.reply = reply;
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

    public ReplyComment getReply() {
        return reply;
    }

    public void setReply(ReplyComment reply) {
        this.reply = reply;
    }

    public Boolean isLiked() {
        return isLiked;
    }

    public void setLike(Boolean liked) {
        isLiked = liked;
    }

    @Override
    public Integer getTargetId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ReplyLike{" +
                "id=" + id +
                ", user=" + user +
                ", reply=" + reply +
                ", isLiked=" + isLiked +
                '}';
    }
}
