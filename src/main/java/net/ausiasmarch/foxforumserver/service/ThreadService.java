package net.ausiasmarch.foxforumserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.foxforumserver.entity.ThreadEntity;
import net.ausiasmarch.foxforumserver.entity.UserEntity;
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

    public ThreadEntity get(Long id) {
        return oThreadRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Thread not found"));
    }

    public Page<ThreadEntity> getPage(Pageable oPageable, Long userId) {
        if (userId == 0) {
            return oThreadRepository.findAll(oPageable);
        } else {
            return oThreadRepository.findByUserId(userId, oPageable);
        }
    }

    public Long create(ThreadEntity oThreadEntity) {
        oThreadEntity.setId(null);
        String strJWTusername = oHttpServletRequest.getAttribute("username").toString();
        UserEntity oUserEntity = oUserRepository.findByUsername(strJWTusername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (Boolean.TRUE.equals(oUserEntity.getRole())) {
            return oThreadRepository.save(oThreadEntity).getId();
        } else {
            oThreadEntity.setUser(oUserEntity);
            return oThreadRepository.save(oThreadEntity).getId();
        }
    }

    public ThreadEntity update(ThreadEntity oThreadEntity) {
        oThreadEntity = oThreadRepository.findById(oThreadEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Reply not found"));
        String strJWTusername = oHttpServletRequest.getAttribute("username").toString();
        UserEntity oUserEntity = oUserRepository.findByUsername(strJWTusername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (Boolean.TRUE.equals(oUserEntity.getRole())) {
            return oThreadRepository.save(oThreadEntity);
        } else {
            if (oThreadEntity.getUser().getId().equals(oUserEntity.getId())) {
                return oThreadRepository.save(oThreadEntity);
            } else {
                throw new ResourceNotFoundException("Unauthorized");
            }
        }
    }

    public Long delete(Long id) {
        ThreadEntity oThreadEntity = oThreadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reply not found"));
        String strJWTusername = oHttpServletRequest.getAttribute("username").toString();
        UserEntity oUserEntity = oUserRepository.findByUsername(strJWTusername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (Boolean.TRUE.equals(oUserEntity.getRole())) {
            oThreadRepository.deleteById(id);
            return id;
        } else {
            if (oThreadEntity.getUser().getId().equals(oUserEntity.getId())) {
                oThreadRepository.deleteById(id);
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
                oThreadRepository
                        .save(new ThreadEntity(DataGenerationHelper.getSpeech(1), oUserService.getOneRandom()));
            }
            return oThreadRepository.count();
        } else {
            throw new ResourceNotFoundException("Unauthorized");
        }
    }

    public ThreadEntity getOneRandom() {
        Pageable oPageable = PageRequest.of((int) (Math.random() * oThreadRepository.count()), 1);
        return oThreadRepository.findAll(oPageable).getContent().get(0);
    }

    @Transactional
    public Long empty() {
        String strJWTusername = oHttpServletRequest.getAttribute("username").toString();
        UserEntity oUserEntity = oUserRepository.findByUsername(strJWTusername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (Boolean.TRUE.equals(oUserEntity.getRole())) {
            oThreadRepository.deleteAll();
            oThreadRepository.resetAutoIncrement();
            oThreadRepository.flush();
            return oThreadRepository.count();
        } else {
            throw new ResourceNotFoundException("Unauthorized");
        }
    }

}
