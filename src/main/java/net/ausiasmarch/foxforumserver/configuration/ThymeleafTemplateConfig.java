package net.ausiasmarch.foxforumserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafTemplateConfig {
    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver oTemplateResolver = new ClassLoaderTemplateResolver();
        
        oTemplateResolver.setPrefix("templates/");
        oTemplateResolver.setSuffix(".html");
        oTemplateResolver.setTemplateMode("HTML5");
        oTemplateResolver.setCharacterEncoding("UTF-8");
        oTemplateResolver.setTemplateMode(TemplateMode.HTML);
        oTemplateResolver.setOrder(1);
        oTemplateResolver.setCheckExistence(true);

        return oTemplateResolver;
    }
}
