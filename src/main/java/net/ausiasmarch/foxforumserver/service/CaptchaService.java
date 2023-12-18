package net.ausiasmarch.foxforumserver.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import net.ausiasmarch.foxforumserver.entity.CaptchaEntity;
import net.ausiasmarch.foxforumserver.repository.CaptchaRepository;

@Service
public class CaptchaService {

    @Autowired
    private CaptchaRepository oCaptchaRepository;

    @Autowired
    private DefaultKaptcha oDefaultKaptcha;

    public CaptchaEntity createCaptcha() {
        CaptchaEntity oCaptchaEntity = new CaptchaEntity();
        String text = oDefaultKaptcha.createText();
        byte[] image = generateCaptchaImage(text);
        oCaptchaEntity.setId(null);
        oCaptchaEntity.setText(text);
        oCaptchaEntity.setImage(image);
        return oCaptchaRepository.save(oCaptchaEntity);
    }

    public CaptchaEntity getRandomCaptcha() {
        
        List<CaptchaEntity> oCaptchaEntityList = oCaptchaRepository.findAll();
            if (!oCaptchaEntityList.isEmpty()) {
               Random oRandom = new Random();
               int index = oRandom.nextInt(oCaptchaEntityList.size());
               return oCaptchaEntityList.get(index);
            } else {
                throw new RuntimeException("No captchas found in database");
            }
    }

     private byte[] generateCaptchaImage(String text) {
        BufferedImage oBufferedImage = oDefaultKaptcha.createImage(text);
        try (ByteArrayOutputStream oByteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(oBufferedImage, "png", oByteArrayOutputStream);
            oByteArrayOutputStream.flush();
            return oByteArrayOutputStream.toByteArray();
        } catch (Exception oException) {
            oException.printStackTrace();
            return new byte[0];
        }
    }

    
    
}
