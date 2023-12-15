package net.ausiasmarch.foxforumserver.bean;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class CaptchaConfigurationBean {

    @Bean
    public DefaultKaptcha oDefaultKaptcha() {
        Properties oProperties = new Properties();
        Config oConfig = new Config(oProperties);
        DefaultKaptcha oDefaultKaptcha = new DefaultKaptcha();
        oDefaultKaptcha.setConfig(oConfig);
        return oDefaultKaptcha;
    }
    
}
