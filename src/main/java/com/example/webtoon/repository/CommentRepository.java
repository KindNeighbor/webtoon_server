package com.example.webtoon.repository;

import com.example.webtoon.entity.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM test_webtoon.comment where episode_id = ?1", nativeQuery = true)
    List<Comment> findAllByEpisodeId(Long episodeId);

    Optional<Comment> findByCommentIdAndUser_UserId(Long commentId, Long userId);

    boolean existsByCommentIdAndUser_UserId(Long episodeId, Long userId);

    void deleteByCommentIdAndUser_UserId(Long episodeId, Long userId);
}
