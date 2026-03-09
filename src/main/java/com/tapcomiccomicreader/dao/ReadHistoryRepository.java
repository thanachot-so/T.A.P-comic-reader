package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.Comic;
import com.tapcomiccomicreader.entity.ReadHistory;
import com.tapcomiccomicreader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReadHistoryRepository extends JpaRepository<ReadHistory,Integer> {

    @Query("SELECT r FROM ReadHistory r " +
            "WHERE r.comic =:comic " +
            "AND r.user = :user ")
    Optional<ReadHistory> findByUserAndComic(@Param("comic") Comic comic,
                                             @Param("user") User user);

    @Query("SELECT r FROM ReadHistory r " +
            "JOIN FETCH r.comic " +
            "JOIN FETCH r.readChapter " +
            "WHERE r.user.uuid = :uuid " +
            "ORDER BY r.lastReadAt DESC")
    List<ReadHistory> findByUuid(@Param("uuid") String uuid);
}
