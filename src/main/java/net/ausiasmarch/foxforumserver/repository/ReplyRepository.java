package net.ausiasmarch.foxforumserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.foxforumserver.entity.ReplyEntity;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long>{
    
}
