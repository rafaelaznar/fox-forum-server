package net.ausiasmarch.foxforumserver.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.ausiasmarch.foxforumserver.dto.EmailValuesDTO;
    
   

@Service("emailService")
public class EmailService {
    
    @Autowired
    JavaMailSender oJavaMailSender;

    @Autowired
    TemplateEngine oTemplateEngine;

    @Value("${mail.urlFront}")
    private String strUrlFront;

    private JavaMailSender  javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    public void sendEmailTemplate(EmailValuesDTO oEmailValuesDTO) {
        MimeMessage oMimeMessage = oJavaMailSender.createMimeMessage();

        try {
            MimeMessageHelper oMimeMessageHelper = new MimeMessageHelper(oMimeMessage, true);
            Context oContext = new Context();
            
            /* Valor del nombre de usuario y jwt token en el mail */
            Map<String, Object> oMapModel = new HashMap<>();
            oMapModel.put("strUserName", oEmailValuesDTO.getUserName());  
            oMapModel.put("url", strUrlFront + oEmailValuesDTO.getTokenPassword());

            /* Se establece el modelo para el contexto */
            oContext.setVariables(oMapModel);

            /* Establecemos la plantilla a utilizar para el texto del mail */
            String strHtmlText = oTemplateEngine.process("email-template", oContext);
            
            /* Se reemplazan los valores de la plantilla por los valores del modelo */
            oMimeMessageHelper.setFrom(oEmailValuesDTO.getMailFrom());
            oMimeMessageHelper.setTo(oEmailValuesDTO.getMailTo());
            oMimeMessageHelper.setSubject(oEmailValuesDTO.getMailSubject());
            oMimeMessageHelper.setText(strHtmlText, true);

            /* Se envia el correo */
            oJavaMailSender.send(oMimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
