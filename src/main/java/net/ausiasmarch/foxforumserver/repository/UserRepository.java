package net.ausiasmarch.foxforumserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.foxforumserver.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUsernameAndPassword(String username, String password);

}
