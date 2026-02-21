package com.tapcomiccomicreader.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comic")
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "cover_url")
    @JsonIgnore
    private String coverUrl;

    @Column(name = "title")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "author")
    private String author;

    @Column(name = "artist")
    private String artist;

    @ManyToMany(mappedBy = "followedComics")
    @JsonIgnore
    private List<User> followers;

    @ElementCollection
    @CollectionTable(name = "comic_genres", joinColumns = @JoinColumn(name = "comic"))
    private List<String> genres;

    @OneToMany(mappedBy = "comic", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Chapter> chapters;

    @Formula("(SELECT COUNT(*) FROM user_follows uf WHERE uf.comic_id = id)")
    private Integer followerCount;

    @Formula("(SELECT COUNT(*) FROM comic_chapter cc WHERE cc.comic_id = id)")
    private Integer chapterCount;

    @JsonProperty
    private Integer getFollowerCount() {
        return followerCount;
    }

    @JsonProperty
    private Integer getChapterCount() {
        return chapterCount;
    }

    public Comic() {
        this.uuid = java.util.UUID.randomUUID().toString();
        this.genres = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.chapters = new ArrayList<>();
    }

    public Comic(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }

    public Comic(String title, String description, String author, String artist) {
        this();
        this.title = title;
        this.description = description;
        this.author = author;
        this.artist = artist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", artist='" + artist + '\'' +
                ", followers=" + followers +
                ", genres=" + genres +
                ", chapters=" + chapters +
                '}';
    }
}
