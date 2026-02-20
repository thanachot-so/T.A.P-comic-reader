package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT u FROM User u " +
            "WHERE u.uuid = :uuid")
    Optional<User> findByUuid(@Param("uuid") String uuid);

    @Query("SELECT u FROM User u " +
            "WHERE u.name = :name " +
            "AND u.password = :password")
    Optional<User> login(@Param("name") String name,
                         @Param("password") String password);

    @Query(value = "SELECT COUNT (*) > 0 FROM user_follows WHERE user_id = :userId AND comic_id = :comicId", nativeQuery = true)
    boolean isFollowed(@Param("userId") int userId,
                       @Param("comicId") int comicId);

    @Modifying
    @Query(value = "INSERT INTO user_follows (user_id, comic_id) VALUES (:userId, :comicId)", nativeQuery = true)
    void followComic(@Param("userId") int userId,
                     @Param("comicId") int comicId);

    @Modifying
    @Query(value = "DELETE FROM user_follows WHERE user_id = :userId AND comic_id = :comicId", nativeQuery = true)
    void removeFollowed(@Param("userId") int userId,
                        @Param("comicId") int comicId);


}
