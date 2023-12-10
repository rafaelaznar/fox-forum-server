package net.ausiasmarch.foxforumserver.api;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.foxforumserver.dto.EmailValuesDTO;
import net.ausiasmarch.foxforumserver.entity.UserEntity;
import net.ausiasmarch.foxforumserver.repository.UserRepository;
import net.ausiasmarch.foxforumserver.service.EmailService;
import net.ausiasmarch.foxforumserver.service.UserService;

@RestController
@RequestMapping("/email")
@CrossOrigin
public class EmailController {
    @Autowired
    EmailService oEmailService;

    @Autowired
    UserService oUserService;

      @Autowired
    UserRepository oUserRepository;

    @Value("${spring.mail.username}")
    private String strMailFrom;

    @PostMapping("/recover-password")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO oEmailValuesDTO) {
      UserEntity oUserEntity = oUserService.getByUsernameOrEmail(oEmailValuesDTO.getStrMailTo());


      oEmailValuesDTO.setStrMailFrom(strMailFrom);
      oEmailValuesDTO.setStrMailTo(oUserEntity.getEmail());
      oEmailValuesDTO.setStrSubject("cambio de contraseña");
      oEmailValuesDTO.setStrUserName(oUserEntity.getUsername());
      /*Generamos el token para recuperar contraseña */
      UUID uuid = UUID.randomUUID();
      String strToken = uuid.toString();
      oEmailValuesDTO.setStrToken(strToken);

      /* Guardamos el token en la base de datos */
      oUserEntity.setTokenPassword(strToken);
      oUserRepository.save(oUserEntity);

      oEmailService.sendEmailTemplate(oEmailValuesDTO);

      return new ResponseEntity("Correo enviado correctamente", HttpStatus.OK);
    }
}
