package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ComicRepository  extends JpaRepository<Comic,Integer> {
    @Query("SELECT c FROM Comic c " +
            "WHERE c.uuid = :uuid")
    Optional<Comic> findComicByUuid(@Param("uuid") String uuid);
}
