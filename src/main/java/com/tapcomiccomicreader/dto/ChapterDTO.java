package com.tapcomiccomicreader.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tapcomiccomicreader.entity.Chapter;

import java.time.LocalDateTime;

public record ChapterDTO(
        Integer id,
        String name,
        int count,
        int pageCount,
        int likeCount,
        int dislikeCount,
        Boolean currentUserVote,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createAt
) {
        public ChapterDTO(Chapter chapter, Boolean currentUserVote) {
                this(
                        chapter.getId(),
                        chapter.getName(),
                        chapter.getCount(),
                        chapter.getPageCount(),
                        chapter.getLikeCount(),
                        chapter.getDislikeCount(),
                        currentUserVote,
                        chapter.getCreateAt()
                );
        }
}
