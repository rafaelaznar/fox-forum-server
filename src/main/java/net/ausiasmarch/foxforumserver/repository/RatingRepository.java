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
import net.ausiasmarch.foxforumserver.entity.UserEntity;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    Page<RatingEntity> findByUserId(Long userId, Pageable pageable);

    Page<RatingEntity> findByReplyId(Long replyId, Pageable pageable);

    Optional<RatingEntity> findByUserAndReply(UserEntity user, ReplyEntity reply);

    List<RatingEntity> findByReplyIdIn(List<Long> replyIds);

    @Modifying
    @Query(value = "ALTER TABLE rating AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
