package net.ausiasmarch.foxforumserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.foxforumserver.entity.PendentEntity;

public interface PendentRepository extends JpaRepository<PendentEntity, Long>{

    Optional<PendentEntity> findByToken(String token);
    
}
