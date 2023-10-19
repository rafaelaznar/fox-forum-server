package net.ausiasmarch.foxforumserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import net.ausiasmarch.foxforumserver.entity.UserEntity;
import net.ausiasmarch.foxforumserver.exception.ResourceNotFoundException;
import net.ausiasmarch.foxforumserver.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository oUserRepository;

    public UserEntity get(Long id) {
        return oUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Long create(UserEntity oUserEntity) {
        oUserEntity.setId(null);
        oUserEntity.setPassword("e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e");
        return oUserRepository.save(oUserEntity).getId();
    }

    public UserEntity update(UserEntity oUserEntity) {
        return oUserRepository.save(oUserEntity);
    }

    public Long delete(Long id) {
        oUserRepository.deleteById(id);
        return id;
    }

    public Page<UserEntity> getPage(Pageable oPageable) {
        return oUserRepository.findAll(oPageable);
    }

    public Long populate(Integer amount) {
        for (int i = 0; i < amount; i++) {
            oUserRepository.save(new UserEntity("name" + i, "surname" + i, "lastname" + i, "email" + i + "@ausiasmarch.net", "username" + i,
                    "e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e", true));
        }
        return oUserRepository.count();
    }

}
