package net.ausiasmarch.foxforumserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.foxforumserver.entity.ThreadEntity;
import net.ausiasmarch.foxforumserver.exception.ResourceNotFoundException;
import net.ausiasmarch.foxforumserver.helper.DataGenerationHelper;
import net.ausiasmarch.foxforumserver.repository.ThreadRepository;
import net.ausiasmarch.foxforumserver.repository.UserRepository;

@Service
public class ThreadService {
    @Autowired
    ThreadRepository oThreadRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    UserRepository oUserRepository;

    @Autowired
    UserService oUserService;

    @Autowired
    SessionService oSessionService;

    public ThreadEntity get(Long id) {
        return oThreadRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Thread not found"));
    }

   public Page<ThreadEntity> getPage(Pageable oPageable, String filter, Long userId) {
    oSessionService.onlyAdmins();
    
    Page<ThreadEntity> page;

    if (userId != null && userId != 0) {
        if (filter == null || filter.isEmpty() || filter.trim().isEmpty()) {
            page = oThreadRepository.findByUserId(userId, oPageable);
        } else {
            page = oThreadRepository.findByTitleContainingIgnoreCase( filter, oPageable);
        }
    } else {
        if (filter == null || filter.isEmpty() || filter.trim().isEmpty()) {
            page = oThreadRepository.findAll(oPageable);
        } else {
            page = oThreadRepository.findByTitleContainingIgnoreCase(filter, oPageable);
        }
    }
    return page;
}
    

    public Page<ThreadEntity> getPageByRepliesNumberDesc(Pageable oPageable, Long userId) {
        if (userId == 0) {
            return oThreadRepository.findThreadsByRepliesNumberDesc(oPageable);
        } else {
            return oThreadRepository.findThreadsByRepliesNumberDescFilterByUserId(userId, oPageable);
        }
    }


    public Long create(ThreadEntity oThreadEntity) {
        oThreadEntity.setId(null);
        oSessionService.onlyAdminsOrUsers();
        if (oSessionService.isUser()) {
            oThreadEntity.setUser(oSessionService.getSessionUser());
            return oThreadRepository.save(oThreadEntity).getId();
        } else {
            return oThreadRepository.save(oThreadEntity).getId();
        }
    }

    public ThreadEntity update(ThreadEntity oThreadEntityToSet) {
        ThreadEntity oThreadEntityFromDatabase = this.get(oThreadEntityToSet.getId());
        oSessionService.onlyAdminsOrUsersWithIisOwnData(oThreadEntityFromDatabase.getUser().getId());
        if (oSessionService.isUser()) {
            if (oThreadEntityToSet.getUser().getId().equals(oSessionService.getSessionUser().getId())) {
                return oThreadRepository.save(oThreadEntityToSet);
            } else {
                throw new ResourceNotFoundException("Unauthorized");
            }
        } else {
            return oThreadRepository.save(oThreadEntityToSet);
        }
    }

    public Long delete(Long id) {
        ThreadEntity oThreadEntityFromDatabase = this.get(id);
        oSessionService.onlyAdminsOrUsersWithIisOwnData(oThreadEntityFromDatabase.getUser().getId());
        oThreadRepository.deleteById(id);
        return id;
    }

    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            oThreadRepository
                    .save(new ThreadEntity(DataGenerationHelper.getSpeech(1), oUserService.getOneRandom()));
        }
        return oThreadRepository.count();
    }

    public ThreadEntity getOneRandom() {
        oSessionService.onlyAdmins();
        Pageable oPageable = PageRequest.of((int) (Math.random() * oThreadRepository.count()), 1);
        return oThreadRepository.findAll(oPageable).getContent().get(0);
    }

    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oThreadRepository.deleteAll();
        oThreadRepository.resetAutoIncrement();
        oThreadRepository.flush();
        return oThreadRepository.count();
    }

}
