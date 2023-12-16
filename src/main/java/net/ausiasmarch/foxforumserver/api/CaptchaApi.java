package net.ausiasmarch.foxforumserver.api;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.foxforumserver.entity.CaptchaEntity;
import net.ausiasmarch.foxforumserver.service.CaptchaService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/captcha")
public class CaptchaApi {

    @Autowired
    private CaptchaService oCaptchaService;    

    @PostMapping("/create")
    public ResponseEntity<CaptchaEntity> create() {
        return ResponseEntity.ok(oCaptchaService.createCaptcha());
    }

    @GetMapping("/random")
    public ResponseEntity<CaptchaEntity> getRandom() {
        return ResponseEntity.ok(oCaptchaService.getRandomCaptcha());
    }
    
}
