package net.ausiasmarch.foxforumserver.service;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.foxforumserver.entity.ReplyEntity;
import net.ausiasmarch.foxforumserver.exception.ResourceNotFoundException;
import net.ausiasmarch.foxforumserver.helper.DataGenerationHelper;
import net.ausiasmarch.foxforumserver.repository.ReplyRepository;
import net.ausiasmarch.foxforumserver.repository.UserRepository;

@Service
public class ReplyService {
    @Autowired
    ReplyRepository oReplyRepository;

    @Autowired
    UserRepository oUserRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    ThreadService oThreadService;

    @Autowired
    UserService oUserService;

    @Autowired
    SessionService oSessionService;

    public ReplyEntity get(Long id) {
        return oReplyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reply not found"));
    }

    public Page<ReplyEntity> getPage(Pageable oPageable, Long userId, Long threadId, String strFilter) {
        if (userId == 0) {
            if (threadId == 0) {
                if (strFilter == null || strFilter.isEmpty()) {
                    return oReplyRepository.findAll(oPageable);
                } else {
                    
                    return oReplyRepository.findByTitleOrBodyContainingIgnoreCase(strFilter, oPageable);
                }
            } else {
                return oReplyRepository.findByThreadId(threadId, oPageable);
            }
        } else {
            return oReplyRepository.findByUserId(userId, oPageable);
        }
    }
    

    public Long create(ReplyEntity oReplyEntity) {
        oSessionService.onlyAdminsOrUsers();
        oReplyEntity.setId(null);
        if (oSessionService.isUser()) {
            oReplyEntity.setUser(oSessionService.getSessionUser());
            return oReplyRepository.save(oReplyEntity).getId();
        } else {
            if (oReplyEntity.getUser().getId() == null || oReplyEntity.getUser().getId() == 0) {
                oReplyEntity.setUser(oSessionService.getSessionUser());
            }
            return oReplyRepository.save(oReplyEntity).getId();
        }
    }

    public ReplyEntity update(ReplyEntity oReplyEntityToSet) {
        ReplyEntity oReplyEntityFromDatabase = this.get(oReplyEntityToSet.getId());
        oSessionService.onlyAdminsOrUsersWithIisOwnData(oReplyEntityFromDatabase.getUser().getId());
        if (oSessionService.isUser()) {
            oReplyEntityToSet.setUser(oSessionService.getSessionUser());
            return oReplyRepository.save(oReplyEntityToSet);
        } else {
            return oReplyRepository.save(oReplyEntityToSet);
        }
    }

    public Long delete(Long id) {
        ReplyEntity oReplyEntityFromDatabase = this.get(id);
        oSessionService.onlyAdminsOrUsersWithIisOwnData(oReplyEntityFromDatabase.getUser().getId());
        oReplyRepository.deleteById(id);
        return id;
    }

    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            oReplyRepository.save(new ReplyEntity(DataGenerationHelper.getSpeech(1),
                    DataGenerationHelper.getSpeech(ThreadLocalRandom.current().nextInt(5, 25)),
                    DataGenerationHelper.getRadomDate(),
                    oUserService.getOneRandom(), oThreadService.getOneRandom()));
        }
        return oReplyRepository.count();
    }

    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oReplyRepository.deleteAll();
        oReplyRepository.resetAutoIncrement();
        oReplyRepository.flush();
        return oReplyRepository.count();
    }

}
