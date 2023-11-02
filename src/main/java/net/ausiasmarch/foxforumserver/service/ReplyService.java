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
    HttpServletRequest oHttpServletRequest;

    @Autowired
    ThreadService oThreadService;

    @Autowired
    UserService oUserService;

    public ReplyEntity get(Long id) {
        return oReplyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reply not found"));
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

    public Long create(ReplyEntity oReplyEntity) {
        oReplyEntity.setId(null);
        String strJWTusername = oHttpServletRequest.getAttribute("username").toString();
        UserEntity oUserEntity = oUserRepository.findByUsername(strJWTusername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (Boolean.TRUE.equals(oUserEntity.getRole())) {
            return oReplyRepository.save(oReplyEntity).getId();
        } else {
            oReplyEntity.setUser(oUserEntity);
            return oReplyRepository.save(oReplyEntity).getId();
        }

    }

    public ReplyEntity update(ReplyEntity oReplyEntity) {
        oReplyEntity = oReplyRepository.findById(oReplyEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Reply not found"));
        String strJWTusername = oHttpServletRequest.getAttribute("username").toString();
        UserEntity oUserEntity = oUserRepository.findByUsername(strJWTusername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (Boolean.TRUE.equals(oUserEntity.getRole())) {
            return oReplyRepository.save(oReplyEntity);
        } else {
            if (oReplyEntity.getUser().getId().equals(oUserEntity.getId())) {
                return oReplyRepository.save(oReplyEntity);
            } else {
                throw new ResourceNotFoundException("Unauthorized");
            }
        }
    }

    public Long delete(Long id) {
        ReplyEntity oReplyEntity = oReplyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reply not found"));
        String strJWTusername = oHttpServletRequest.getAttribute("username").toString();
        UserEntity oUserEntity = oUserRepository.findByUsername(strJWTusername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (Boolean.TRUE.equals(oUserEntity.getRole())) {
            oReplyRepository.deleteById(id);
            return id;
        } else {
            if (oReplyEntity.getUser().getId().equals(oUserEntity.getId())) {
                oReplyRepository.deleteById(id);
                return id;
            } else {
                throw new ResourceNotFoundException("Unauthorized");
            }
        }
    }

    public Long populate(Integer amount) {
        String strJWTusername = oHttpServletRequest.getAttribute("username").toString();
        UserEntity oUserEntity = oUserRepository.findByUsername(strJWTusername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (Boolean.TRUE.equals(oUserEntity.getRole())) {
            for (int i = 0; i < amount; i++) {
                oReplyRepository.save(new ReplyEntity(DataGenerationHelper.getSpeech(1),
                        DataGenerationHelper.getSpeech(ThreadLocalRandom.current().nextInt(5, 25)),
                        oUserService.getOneRandom(), oThreadService.getOneRandom()));
            }
            return oReplyRepository.count();
        } else {
            throw new ResourceNotFoundException("Unauthorized");
        }

    }
}
