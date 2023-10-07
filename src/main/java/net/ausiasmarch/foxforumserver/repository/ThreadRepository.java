package net.ausiasmarch.foxforumserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.foxforumserver.entity.ThreadEntity;

public interface ThreadRepository extends JpaRepository<ThreadEntity, Long>{
    
}
