package com.tapcomiccomicreader.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateComicRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String author;
    private String artist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "CreateComicRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
