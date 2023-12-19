package net.ausiasmarch.foxforumserver.bean;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class CaptchaConfigurationBean {

    @Bean
    public DefaultKaptcha oDefaultKaptcha() {
        Properties oProperties = new Properties();
        oProperties.setProperty("kaptcha.border", "yes");
        oProperties.setProperty("kaptcha.border.color", "105,179,90");
        oProperties.setProperty("kaptcha.textproducer.font.color", "0,0,0");
        oProperties.setProperty("kaptcha.image.width", "200");
        oProperties.setProperty("kaptcha.image.height", "50");
        oProperties.setProperty("kaptcha.textproducer.font.size", "40");
        oProperties.setProperty("kaptcha.session.key", "captchaCode");
        oProperties.setProperty("kaptcha.textproducer.char.length", "6");
        Config oConfig = new Config(oProperties);
        DefaultKaptcha oDefaultKaptcha = new DefaultKaptcha();
        oDefaultKaptcha.setConfig(oConfig);
        return oDefaultKaptcha;
    }
    
}
