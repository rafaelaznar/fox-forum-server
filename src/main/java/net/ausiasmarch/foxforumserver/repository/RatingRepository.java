package net.ausiasmarch.foxforumserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.foxforumserver.entity.RatingEntity;
import net.ausiasmarch.foxforumserver.entity.ReplyEntity;
import net.ausiasmarch.foxforumserver.entity.ThreadEntity;
import net.ausiasmarch.foxforumserver.entity.UserEntity;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    Page<RatingEntity> findByUserId(Long userId, Pageable pageable);

    Page<RatingEntity> findByThreadId(Long threadId, Pageable pageable);

    Page<RatingEntity> findByReplyId(Long replyId, Pageable pageable);

    // Busca si un usuario ya ha valorado y si eso se actualiza
    Optional<RatingEntity> findByUserAndThread(UserEntity user, ThreadEntity thread);

    Optional<RatingEntity> findByUserAndReply(UserEntity user, ReplyEntity reply);

    Optional<RatingEntity> findByUserAndThreadAndReply(UserEntity user, ThreadEntity thread, ReplyEntity reply);

    @Modifying
    @Query(value = "ALTER TABLE rating AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    List<RatingEntity> findByReplyId(Long replyId);

    List<RatingEntity> findByReplyIdIn(List<Long> replyIds);

    Optional<RatingEntity> findByReplyIdAndUserId(Long replyId, Long userId);
}
