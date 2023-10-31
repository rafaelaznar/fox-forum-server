package net.ausiasmarch.foxforumserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.foxforumserver.entity.ThreadEntity;


public interface ThreadRepository extends JpaRepository<ThreadEntity, Long> {
    Page<ThreadEntity> findByUserId(Long id, Pageable pageable);
}
