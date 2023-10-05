package net.ausiasmarch.foxforumserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.foxforumserver.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    
}
