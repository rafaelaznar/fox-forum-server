package net.ausiasmarch.foxforumserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.foxforumserver.bean.CaptchaBean;
import net.ausiasmarch.foxforumserver.bean.CaptchaResponseBean;
import net.ausiasmarch.foxforumserver.bean.UserBean;
import net.ausiasmarch.foxforumserver.service.SessionService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    SessionService oSessionService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserBean oUserBean) {
        return ResponseEntity.ok("\"" + oSessionService.login(oUserBean) + "\"");
    }

    @GetMapping("/prelogin")
    public ResponseEntity<CaptchaResponseBean> prelogin() {
        return ResponseEntity.ok(oSessionService.prelogin());
    }

    @PostMapping("/loginCaptcha") 
    public ResponseEntity<String> loginCaptcha(@RequestBody CaptchaBean oCaptchaBean) {
        return ResponseEntity.ok("\"" + oSessionService.loginCaptcha(oCaptchaBean) + "\"");
    }

}
