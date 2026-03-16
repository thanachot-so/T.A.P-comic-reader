package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            "WHERE u.name = :username")
    Optional<User> findByName(@Param("username") String username);

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

    @Query("SELECT COUNT (*) > 0 FROM User u WHERE u.name = :name")
    boolean isExist(@Param("name") String name);

    @Query(value = "SELECT * ," +
            "(SELECT COUNT(*) FROM user_follows uf WHERE uf.user_id = users.id) AS followedComicsCount," +
            "(SELECT COUNT(*) FROM user_friends ufr WHERE ufr.user_id = users.id) AS friends " +
            "FROM users " +
            "WHERE name ILIKE '%' || :keyword || '%' OR name % :keyword " +
            "ORDER BY similarity(name, :keyword) DESC", nativeQuery = true)
    Page<User> searchByName(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT f FROM User u JOIN u.friends f WHERE u.id = :userId")
    Page<User> findFriend(@Param("userId") int userId ,Pageable pageable);

}
