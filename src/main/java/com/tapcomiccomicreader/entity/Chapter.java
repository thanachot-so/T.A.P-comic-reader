package com.tapcomiccomicreader.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comic_chapter")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "chapter_name")
    private String name;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "comic_id", nullable = false)
    @JsonBackReference
    private Comic comic;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Page> pages;

    public Chapter() {
        this.pages = new ArrayList<>();
    }

    public Chapter(String name) {
        this();
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", comic=" + comic +
                ", pages=" + pages +
                '}';
    }
}
