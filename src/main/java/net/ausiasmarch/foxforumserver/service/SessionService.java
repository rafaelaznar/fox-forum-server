package net.ausiasmarch.foxforumserver.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.foxforumserver.bean.CaptchaBean;
import net.ausiasmarch.foxforumserver.bean.CaptchaResponseBean;
import net.ausiasmarch.foxforumserver.bean.UserBean;
import net.ausiasmarch.foxforumserver.entity.CaptchaEntity;
import net.ausiasmarch.foxforumserver.entity.PendentEntity;
import net.ausiasmarch.foxforumserver.entity.UserEntity;
import net.ausiasmarch.foxforumserver.exception.ResourceNotFoundException;
import net.ausiasmarch.foxforumserver.exception.UnauthorizedException;
import net.ausiasmarch.foxforumserver.helper.DataGenerationHelper;
import net.ausiasmarch.foxforumserver.helper.JWTHelper;
import net.ausiasmarch.foxforumserver.repository.CaptchaRepository;
import net.ausiasmarch.foxforumserver.repository.PendentRepository;
import net.ausiasmarch.foxforumserver.repository.UserRepository;

@Service
public class SessionService {

    @Autowired
    UserRepository oUserRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    CaptchaService oCaptchaService;

    @Autowired
    PendentRepository oPendentRepository;

    @Autowired
    CaptchaRepository oCaptchaRepository;

    public String login(UserBean oUserBean) {
        oUserRepository.findByUsernameAndPassword(oUserBean.getUsername(), oUserBean.getPassword())
                .orElseThrow(() -> new ResourceNotFoundException("Wrong User or password"));
        return JWTHelper.generateJWT(oUserBean.getUsername());
    }

    public String getSessionUsername() {        
        if (oHttpServletRequest.getAttribute("username") instanceof String) {
            return oHttpServletRequest.getAttribute("username").toString();
        } else {
            return null;
        }
    }

    public UserEntity getSessionUser() {
        if (this.getSessionUsername() != null) {
            return oUserRepository.findByUsername(this.getSessionUsername()).orElse(null);    
        } else {
            return null;
        }
    }

    public Boolean isSessionActive() {
        if (this.getSessionUsername() != null) {
            return oUserRepository.findByUsername(this.getSessionUsername()).isPresent();
        } else {
            return false;
        }
    }

    public Boolean isAdmin() {
        if (this.getSessionUsername() != null) {
            UserEntity oUserEntityInSession = oUserRepository.findByUsername(this.getSessionUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            return Boolean.FALSE.equals(oUserEntityInSession.getRole());
        } else {
            return false;
        }
    }

    public Boolean isUser() {
        if (this.getSessionUsername() != null) {
            UserEntity oUserEntityInSession = oUserRepository.findByUsername(this.getSessionUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            return Boolean.TRUE.equals(oUserEntityInSession.getRole());
        } else {
            return false;
        }
    }

    public void onlyAdmins() {
        if (!this.isAdmin()) {
            throw new UnauthorizedException("Only admins can do this");
        }
    }

    public void onlyUsers() {
        if (!this.isUser()) {
            throw new UnauthorizedException("Only users can do this");
        }
    }

    public void onlyAdminsOrUsers() {
        if (!this.isSessionActive()) {
            throw new UnauthorizedException("Only admins or users can do this");
        }
    }

    public void onlyUsersWithIisOwnData(Long id_user) {
        if (!this.isUser()) {
            throw new UnauthorizedException("Only users can do this");
        }
        if (!this.getSessionUser().getId().equals(id_user)) {
            throw new UnauthorizedException("Only users can do this");
        }
    }

    public void onlyAdminsOrUsersWithIisOwnData(Long id_user) {
        if (this.isSessionActive()) {
            if (!this.isAdmin()) {
                if (!this.isUser()) {
                    throw new UnauthorizedException("Only admins or users can do this");
                } else {
                    if (!this.getSessionUser().getId().equals(id_user)) {
                        throw new UnauthorizedException("Only admins or users with its own data can do this");
                    }
                }
            }
        } else {
            throw new UnauthorizedException("Only admins or users can do this");
        }
    }

    @Transactional
    public CaptchaResponseBean prelogin() {
    
        CaptchaEntity oCaptchaEntity = oCaptchaService.getRandomCaptcha();
    
        PendentEntity oPendentEntity = new PendentEntity();
        oPendentEntity.setCaptcha(oCaptchaEntity);
        oPendentEntity.setTimecode(LocalDateTime.now());
        PendentEntity oNewPendentEntity = oPendentRepository.save(oPendentEntity);

        
        oNewPendentEntity.setToken(DataGenerationHelper.getSHA256(
            String.valueOf(oNewPendentEntity.getId()) 
            + String.valueOf(oCaptchaEntity.getId())
            + String.valueOf(DataGenerationHelper.getRandomInt(0, 9999))));
       

        oPendentRepository.save(oNewPendentEntity);

        CaptchaResponseBean oCaptchaResponseBean = new CaptchaResponseBean();
        oCaptchaResponseBean.setToken(oNewPendentEntity.getToken());
        oCaptchaResponseBean.setCaptchaImage(oNewPendentEntity.getCaptcha().getImage());

        return oCaptchaResponseBean;
        
    }

    public String loginCaptcha(@RequestBody CaptchaBean oCaptchaBean) {
        if (oCaptchaBean.getUsername() != null && oCaptchaBean.getPassword() != null) {
            UserEntity oUserEntity = oUserRepository.findByUsernameAndPassword(oCaptchaBean.getUsername(), oCaptchaBean.getPassword()).orElseThrow(() -> new ResourceNotFoundException("Wrong User or password"));
            if (oUserEntity!=null) {
                PendentEntity oPendentEntity = oPendentRepository.findByToken(oCaptchaBean.getToken()).orElseThrow(() -> new ResourceNotFoundException("Pendent not found"));

                LocalDateTime timecode = oPendentEntity.getTimecode();

                if (LocalDateTime.now().isAfter(timecode.plusSeconds(120))) {
                    throw new UnauthorizedException("Captcha expired");
                }

                if (oPendentEntity.getCaptcha().getText().trim().equals(oCaptchaBean.getAnswer().trim())) {
                    oPendentRepository.delete(oPendentEntity);
                    return JWTHelper.generateJWT(oCaptchaBean.getUsername());
                } else {
                    throw new UnauthorizedException("Wrong captcha");
                }
            } else {
                throw new UnauthorizedException("Wrong User or password");
            }        
        } else {
            throw new UnauthorizedException("User or password not found");
        }
    }



}
