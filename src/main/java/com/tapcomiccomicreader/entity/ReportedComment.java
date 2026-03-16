package com.tapcomiccomicreader.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tapcomiccomicreader.helperclass.ReportStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reported_comments")
public class ReportedComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @JsonIgnore
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    @JsonIgnore
    private ReplyComment reply;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReportStatus status = ReportStatus.PENDING;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @PrePersist
    public void onCreate() {
        createAt = LocalDateTime.now();
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public ReplyComment getReply() {
        return reply;
    }

    public void setReply(ReplyComment reply) {
        this.reply = reply;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @JsonProperty
    public String getUuid() {
        return user.getUuid();
    }

    @JsonProperty
    public Integer getCommentId() {
        return comment != null ? comment.getId() : null;
    }

    @JsonProperty
    public Integer getReplyId() {
        return reply != null ? reply.getId() : null;
    }

    @Override
    public String toString() {
        return "ReportedComment{" +
                "id=" + id +
                ", user=" + user +
                ", comment=" + comment +
                ", reply=" + reply +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                ", createAt=" + createAt +
                '}';
    }
}
