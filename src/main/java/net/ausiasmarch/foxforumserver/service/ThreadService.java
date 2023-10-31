package net.ausiasmarch.foxforumserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import net.ausiasmarch.foxforumserver.entity.ThreadEntity;
import net.ausiasmarch.foxforumserver.exception.ResourceNotFoundException;
import net.ausiasmarch.foxforumserver.helper.DataGenerationHelper;
import net.ausiasmarch.foxforumserver.repository.ThreadRepository;

@Service
public class ThreadService {
    @Autowired
    ThreadRepository oThreadRepository;

    public ThreadEntity get(Long id) {
        return oThreadRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Thread not found"));
    }

    public Long create(ThreadEntity oThreadEntity) {
        oThreadEntity.setId(null);
        return oThreadRepository.save(oThreadEntity).getId();
    }

    public ThreadEntity update(ThreadEntity oThreadEntity) {
        return oThreadRepository.save(oThreadEntity);
    }

    public Long delete(Long id) {
        oThreadRepository.deleteById(id);
        return id;
    }

    public Page<ThreadEntity> getPage(Pageable oPageable, Long id_user) {
        return oThreadRepository.findByUserId(id_user,oPageable);
    }

    public ThreadEntity getOneRandom() {
        Pageable oPageable = PageRequest.of((int) (Math.random() * oThreadRepository.count()), 1);
        return oThreadRepository.findAll(oPageable).getContent().get(0);
    }

    @Autowired
    UserService oUserService;

    public Long populate(Integer amount) {
        for (int i = 0; i < amount; i++) {
            oThreadRepository.save(new ThreadEntity(DataGenerationHelper.getSpeech(1), oUserService.getOneRandom()));
        }
        return oThreadRepository.count();
    }

}
