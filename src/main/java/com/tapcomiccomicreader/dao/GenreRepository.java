package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre,Integer> {

    @Query("SELECT g FROM Genre g " +
            "WHERE g.id = :genreId")
    Optional<Genre> findById(@Param("genreId") int genreId);

}
