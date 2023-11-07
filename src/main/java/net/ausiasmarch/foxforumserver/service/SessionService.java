package net.ausiasmarch.foxforumserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.foxforumserver.bean.UserBean;
import net.ausiasmarch.foxforumserver.entity.UserEntity;
import net.ausiasmarch.foxforumserver.exception.ResourceNotFoundException;
import net.ausiasmarch.foxforumserver.helper.JWTHelper;
import net.ausiasmarch.foxforumserver.repository.UserRepository;

@Service
public class SessionService {

    @Autowired
    UserRepository oUserRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    public String login(UserBean oUserBean) {
        oUserRepository.findByUsernameAndPassword(oUserBean.getUsername(), oUserBean.getPassword())
                .orElseThrow(() -> new ResourceNotFoundException("Wrong User or password"));
        return JWTHelper.generateJWT(oUserBean.getUsername());
    }

    public UserEntity getSessionUser() {
        String strJWTusername = oHttpServletRequest.getAttribute("username").toString();
        return oUserRepository.findByUsername(strJWTusername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Boolean isSessionActive() {
        String strJWTusername = oHttpServletRequest.getAttribute("username").toString();
        return oUserRepository.findByUsername(strJWTusername).isPresent();
    }

    public Boolean isAdmin() {
        String strJWTusername = oHttpServletRequest.getAttribute("username").toString();
        UserEntity oUserEntityInSession = oUserRepository.findByUsername(strJWTusername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return Boolean.FALSE.equals(oUserEntityInSession.getRole());
    }

    public Boolean isUser() {
        String strJWTusername = oHttpServletRequest.getAttribute("username").toString();
        UserEntity oUserEntityInSession = oUserRepository.findByUsername(strJWTusername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return Boolean.TRUE.equals(oUserEntityInSession.getRole());
    }

    public void onlyAdmins() {
        if (!this.isAdmin()) {
            throw new ResourceNotFoundException("Only admins can do this");
        }
    }

    public void onlyUsers() {
        if (!this.isUser()) {
            throw new ResourceNotFoundException("Only users can do this");
        }
    }

    public void onlyAdminsOrUsers() {
        if (!this.isSessionActive()) {
            throw new ResourceNotFoundException("Only admins or users can do this");
        }
    }

    public void onlyUsersWithIisOwnData(Long id_user) {
        if (!this.isUser()) {
            throw new ResourceNotFoundException("Only users can do this");
        }
        if (!this.getSessionUser().getId().equals(id_user)) {
            throw new ResourceNotFoundException("Only users can do this");
        }
    }

    public void onlyAdminsOrUsersWithIisOwnData(Long id_user) {
        if (this.isSessionActive()) {
            if (!this.isAdmin()) {
                if (!this.isUser()) {
                    throw new ResourceNotFoundException("Only admins or users can do this");
                } else {
                    if (!this.getSessionUser().getId().equals(id_user)) {
                        throw new ResourceNotFoundException("Only admins or users with its own data can do this");
                    }
                }
            }
        } else {
            throw new ResourceNotFoundException("Only admins or users can do this");
        }
    }

}
