package net.ausiasmarch.foxforumserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.foxforumserver.entity.CaptchaEntity;

public interface CaptchaRepository extends JpaRepository<CaptchaEntity, Long> {
    
}
