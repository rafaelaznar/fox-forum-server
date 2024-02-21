package net.ausiasmarch.foxforumserver.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.foxforumserver.entity.UserEntity;
import net.ausiasmarch.foxforumserver.exception.ResourceNotFoundException;
import net.ausiasmarch.foxforumserver.helper.DataGenerationHelper;
import net.ausiasmarch.foxforumserver.repository.UserRepository;

@Service
public class UserService {

    private final String foxforumPASSWORD = "e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e";

    @Autowired
    UserRepository oUserRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    SessionService oSessionService;

    @Autowired
    EmailService oEmailService;

    public UserEntity get(Long id) {
        if (oSessionService.isAdmin()) {
            oSessionService.onlyAdmins();
            return oUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        } else {
            return oUserRepository.findByActiveTrue(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not enabled"));
        }
    }

    // LLamada desde RatingAjaxService - UserAjaxService
    public UserEntity getByUsername(String username) {
        return oUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by username"));
    }

    public UserEntity getByEmail(String email) {
        return oUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by email"));
    }

    public UserEntity getByTokenPassword(String tokenPassword) {
        return oUserRepository.findByTokenPassword(tokenPassword)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by token"));
    }

    public Page<UserEntity> getPage(Pageable pageable, String filter) {
        oSessionService.onlyAdmins();

        Page<UserEntity> page;

        if (filter == null || filter.isEmpty() || filter.trim().isEmpty()) {
            if (oSessionService.isAdmin()) {
                page = oUserRepository.findAll(pageable);
            } else {
                page = oUserRepository.findAllByActiveTrue(pageable);
            }
        } else {
            page = oUserRepository.findByUserByNameOrSurnameOrLastnameContainingIgnoreCase(
                    filter, filter, filter, filter, pageable);
        }
        return page;
    }

    public Page<UserEntity> getPageByRepliesNumberDesc(Pageable oPageable) {
        if (oSessionService.isAdmin()) {
            return oUserRepository.findUsersByRepliesNumberDescFilter(oPageable);
        } else {
            return oUserRepository.findUsersByRepliesNumberDescFilterActiveTrue(oPageable);
        }
    }

    public Page<UserEntity> getPage(Pageable oPageable) {
        oSessionService.onlyAdmins();
        return oUserRepository.findAll(oPageable);
    }

    public Long create(UserEntity oUserEntity) {
        oSessionService.onlyAdmins();
        oUserEntity.setId(null);
        oUserEntity.setPassword(foxforumPASSWORD);
        oUserEntity.setToken(UUID.randomUUID().toString()); // genero el token
        oUserRepository.save(oUserEntity);
        this.sendEmail(oUserEntity); // envio el email
        return oUserEntity.getId();
    }

    public Long createForUsers(UserEntity oUserEntity) {
        oUserEntity.setId(null);
        oUserEntity.setPassword(foxforumPASSWORD);
        oUserEntity.setToken(UUID.randomUUID().toString()); // genero el token
        oUserEntity.setRole(true); // role = true -> user
        oUserRepository.save(oUserEntity);
        this.sendEmail(oUserEntity); // envio el email
        return oUserEntity.getId();
    }

    /**
     * Send email to user with token
     * 
     * @param user
     */
    public void sendEmail(UserEntity user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:4200/user/confirm-account?token=" + user.getToken());
        oEmailService.sendEmail(mailMessage);
    }

    /*
     * Confirm email
     */
    public ResponseEntity<?> confirmCorreo(String tokenVerificacion, String password) {
        UserEntity oUser = oUserRepository.findByToken(tokenVerificacion)
                .orElseThrow(() -> new RuntimeException("Token not found when validatimg token"));
        oUser.setVerified(true);
        oUser.setToken(null);
        oUser.setPassword(password);
        oUserRepository.save(oUser);
        return ResponseEntity.ok("Email verified successfully!");
        // return oUserRepository.save(oUserEntity).getId();
    }

    public UserEntity update(UserEntity oUserEntityToSet) {
        UserEntity oUserEntityFromDatabase = this.get(oUserEntityToSet.getId());
        oSessionService.onlyAdminsOrUsersWithIisOwnData(oUserEntityFromDatabase.getId());
        if (oSessionService.isUser()) {
            oUserEntityToSet.setActive(oUserEntityFromDatabase.isActive());
            oUserEntityToSet.setRole(oUserEntityFromDatabase.getRole());
            oUserEntityToSet.setPassword(foxforumPASSWORD);
            return oUserRepository.save(oUserEntityToSet);
        } else {
            if (oSessionService.isUser()) {
                oUserEntityToSet.setRole(oUserEntityFromDatabase.getRole());
                oUserEntityToSet.setPassword(foxforumPASSWORD);
                return oUserRepository.save(oUserEntityToSet);
            } else {
                oUserEntityToSet.setPassword(foxforumPASSWORD);
                return oUserRepository.save(oUserEntityToSet);
            }
        }
    }

    public Long delete(Long id) {
        oSessionService.onlyAdmins();
        oUserRepository.deleteById(id);
        return id;
    }

    public UserEntity getOneRandom() {
        oSessionService.onlyAdmins();
        Pageable oPageable = PageRequest.of((int) (Math.random() * oUserRepository.count()), 1);
        return oUserRepository.findAll(oPageable).getContent().get(0);
    }

    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            String name = DataGenerationHelper.getRadomName();
            String surname = DataGenerationHelper.getRadomSurname();
            String lastname = DataGenerationHelper.getRadomSurname();
            String email = (name.substring(0, 3) + surname.substring(0, 3) + lastname.substring(0, 2) + i).toLowerCase()
                    + "@ausiasmarch.net";
            String username = DataGenerationHelper
                    .doNormalizeString(
                            name.substring(0, 3) + surname.substring(1, 3) + lastname.substring(1, 2) + i)
                    .toLowerCase();
            // Aquí usamos el constructor correcto con todos los argumentos necesarios
            oUserRepository.save(new UserEntity(null, name, surname, lastname, email, username,
                    "e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e", true, true, true, null));
        }
        return oUserRepository.count();
    }

    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oUserRepository.deleteAll();
        oUserRepository.resetAutoIncrement();
        UserEntity oUserEntity1 = new UserEntity(1L, "Pedro", "Picapiedra", "Roca",
                "pedropicapiedra@ausiasmarch.net", "pedropicapiedra", foxforumPASSWORD, false, true);
        oUserRepository.save(oUserEntity1);

        oUserEntity1 = new UserEntity(2L, "Pablo", "Mármol", "Granito", "pablomarmol@ausiasmarch.net",
                "pablomarmol", foxforumPASSWORD, true, true);
        oUserRepository.save(oUserEntity1);
        return oUserRepository.count();
    }

    @Transactional
    public UserEntity updateCoordinates(Long userId, Double latitude, Double longitude) {
    UserEntity userEntity = this.get(userId);
    userEntity.setLatitude(latitude);
    userEntity.setLongitude(longitude);
    return oUserRepository.save(userEntity);
}

}
