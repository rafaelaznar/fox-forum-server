package net.ausiasmarch.foxforumserver.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import net.ausiasmarch.foxforumserver.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
  Page<UserEntity> findByNameContaining(String name, Pageable pageable);
    Optional<UserEntity> findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT u.*,count(r.id) FROM user u, reply r WHERE u.id = r.id_user GROUP BY u.id ORDER BY COUNT(u.id) desc", nativeQuery = true)
    Page<UserEntity> findUsersByRepliesNumberDescFilter(Pageable pageable);
   
   @Query(value = "SELECT * FROM user WHERE name LIKE %?1% OR surname LIKE %?1% OR lastname LIKE %?1% OR username LIKE %?1% OR email LIKE %?1%", nativeQuery = true)
    Page<UserEntity> findByUserByNameOrSurnameOrLastnameContainingIgnoreCase(String searchText,
            String filter, String filter2, String filter3, Pageable pageable);

    @Modifying
    @Query(value = "ALTER TABLE user AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
