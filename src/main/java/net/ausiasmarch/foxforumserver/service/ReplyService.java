package net.ausiasmarch.foxforumserver.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.foxforumserver.entity.ReplyEntity;
import net.ausiasmarch.foxforumserver.exception.ResourceNotFoundException;
import net.ausiasmarch.foxforumserver.repository.ReplyRepository;

@Service
public class ReplyService {
    @Autowired
    ReplyRepository oReplyRepository;

    public ReplyEntity get(Long id) {
        return oReplyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Thread not found"));
    }

    public ReplyEntity create(ReplyEntity oReplyEntity) {
        oReplyEntity.setId(null);
        return oReplyRepository.save(oReplyEntity);
    }

    public ReplyEntity update(ReplyEntity oReplyEntity) {
        return oReplyRepository.save(oReplyEntity);
    }

    public ReplyEntity delete(Long id) {
        ReplyEntity oReplyEntityaux = oReplyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Thread not found"));
        oReplyRepository.deleteById(id);
        return oReplyEntityaux;
    }

    public Page<ReplyEntity> getPage(Pageable oPageable) {
        return oReplyRepository.findAll(oPageable);
    }

    public Long populate(Integer amount) {
        for (int i = 0; i < amount; i++) {
            oReplyRepository.save(new ReplyEntity("title" + i, "body" + i));
        }
        return oReplyRepository.count();
    }
}
