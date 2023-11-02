package net.ausiasmarch.foxforumserver.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.foxforumserver.entity.ReplyEntity;
import net.ausiasmarch.foxforumserver.entity.UserEntity;
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
    HttpServletRequest oRequest;

    public ReplyEntity get(Long id) {
        return oReplyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reply not found"));
    }

    public Long create(ReplyEntity oReplyEntity) {    
        oReplyEntity.setId(null);
        return oReplyRepository.save(oReplyEntity).getId();
    }

    public ReplyEntity update(ReplyEntity oReplyEntity) {
        return oReplyRepository.save(oReplyEntity);
    }

    public Long delete(Long id) {

        // ejemplo de clase:
        // si el usuario es user: si el reply es suyo entonces puede borrarlo
        // si el usuario es admin: puede borrarlo

        ReplyEntity oReplyEntity = oReplyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reply not found"));
        String username = oRequest.getAttribute("username").toString();
        UserEntity oUserEntity = oUserRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (oUserEntity.getRole()) {
            oReplyRepository.deleteById(id);
            return id;
        } else {
            if (oReplyEntity.getUser().getId() == oUserEntity.getId()) {
                oReplyRepository.deleteById(id);
                return id;
            } else {
                throw new ResourceNotFoundException("Unauthorized");
            }
        }
    }

    public Page<ReplyEntity> getPage(Pageable oPageable, Long userId, Long threadId) {
        if (userId == 0) {
            if (threadId == 0) {
                return oReplyRepository.findAll(oPageable);
            } else {
                return oReplyRepository.findByThreadId(threadId, oPageable);
                
            }            
        } else {
            return oReplyRepository.findByUserId(userId, oPageable);
        }

    }

    @Autowired
    ThreadService oThreadService;

    @Autowired
    UserService oUserService;

    public Long populate(Integer amount) {
        for (int i = 0; i < amount; i++) {
            oReplyRepository.save(new ReplyEntity(DataGenerationHelper.getSpeech(1),
                    DataGenerationHelper.getSpeech(ThreadLocalRandom.current().nextInt(5, 25)),
                    oUserService.getOneRandom(), oThreadService.getOneRandom()));
        }
        return oReplyRepository.count();
    }
}
