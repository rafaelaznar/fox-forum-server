package net.ausiasmarch.foxforumserver.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ausiasmarch.foxforumserver.entity.ThreadEntity;

public interface ThreadRepository extends JpaRepository<ThreadEntity, Long> {

    @Query("SELECT t FROM ThreadEntity t WHERE t.active = true AND t.id = :id")
    Optional<ThreadEntity> findByActiveTrue(@Param("id") Long id);

    @Query("SELECT t FROM ThreadEntity t WHERE t.active")
    Page<ThreadEntity> findAllByActiveTrue(Pageable pageable);

    
    Page<ThreadEntity> findByUserId(Long id, Pageable pageable);

    @Query(value = "SELECT t.*,count(r.id) FROM thread t, reply r WHERE t.id = r.id_thread GROUP BY t.id ORDER BY COUNT(r.id) desc", nativeQuery = true)
    Page<ThreadEntity> findThreadsByRepliesNumberDesc(Pageable pageable);

    @Query(value = "SELECT t.*, COUNT(r.id) FROM thread t JOIN reply r ON t.id = r.id_thread WHERE t.active = true GROUP BY t.id ORDER BY COUNT(r.id) DESC", nativeQuery = true)
    Page<ThreadEntity> findThreadsByRepliesNumberDescActiveTrue(Pageable pageable);

    @Query(value = "SELECT t.*,count(r.id) FROM thread t, reply r WHERE t.id = r.id_thread and t.id_user=$1 GROUP BY t.id ORDER BY COUNT(r.id) desc", nativeQuery = true)
    Page<ThreadEntity> findThreadsByRepliesNumberDescFilterByUserId(Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM thread WHERE LENGTH(?1) >= 3 AND (title LIKE %?1%)", nativeQuery = true)
    Page<ThreadEntity> findByTitleContainingIgnoreCase(String searchText, Pageable pageable);

    @Modifying
    @Query(value = "ALTER TABLE thread AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
