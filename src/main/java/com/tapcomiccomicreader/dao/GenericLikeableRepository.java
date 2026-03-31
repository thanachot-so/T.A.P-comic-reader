package com.tapcomiccomicreader.dao;

import com.tapcomiccomicreader.helperclass.Likeable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenericLikeableRepository<T extends Likeable> extends JpaRepository<T, Integer> {
    Optional<T> findByUserIdAndTargetId(@Param("userId") int userId,
                                        @Param("targetId") int targetId);

    List<T> findByUserIdAndTargetIds(@Param("userId") int userId,
                                     @Param("targetIds") List<Integer> targetIds);

}
