package net.ausiasmarch.foxforumserver.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.foxforumserver.entity.ReplyEntity;
import net.ausiasmarch.foxforumserver.exception.ResourceNotFoundException;
import net.ausiasmarch.foxforumserver.helper.DataGenerationHelper;
import net.ausiasmarch.foxforumserver.repository.ReplyRepository;

@Service
public class ReplyService {
    @Autowired
    ReplyRepository oReplyRepository;

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
        oReplyRepository.deleteById(id);
        return id;
    }

    public Page<ReplyEntity> getPage(Pageable oPageable) {
        return oReplyRepository.findAll(oPageable);
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
