package net.ausiasmarch.foxforumserver.api;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import net.ausiasmarch.foxforumserver.dto.ChangePasswordDTO;
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
      UserEntity oUserEntity = oUserService.getByEmail(oEmailValuesDTO.getMailTo());

      oEmailValuesDTO.setMailFrom(strMailFrom);
      oEmailValuesDTO.setMailTo(oUserEntity.getEmail());
      oEmailValuesDTO.setMailSubject("cambio de contraseña");
      oEmailValuesDTO.setUserName(oUserEntity.getUsername());
      /*Generamos el token para recuperar contraseña */
      UUID uuid = UUID.randomUUID();
      String strToken = uuid.toString();
      oEmailValuesDTO.setTokenPassword(strToken);

      /* Guardamos el token en la base de datos */
      oUserEntity.setTokenPassword(strToken);
      oUserRepository.save(oUserEntity);

      oEmailService.sendEmailTemplate(oEmailValuesDTO);

      return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO oChangePasswordDTO, BindingResult oBindingResult) {
      if (oBindingResult.hasErrors()) {
        return new ResponseEntity("Compruebe los campos introducidos", HttpStatus.BAD_REQUEST);
      }

      if(!oChangePasswordDTO.getPassword().equals(oChangePasswordDTO.getConfirmPassword())) {
        return new ResponseEntity("Las contraseñas no coinciden", HttpStatus.BAD_REQUEST);
      }

      UserEntity oUserEntity = oUserService.getByTokenPassword(oChangePasswordDTO.getTokenPassword());
      //No acepta contraseñas sin cifrar
      oUserEntity.setPassword(oChangePasswordDTO.getPassword());
      oUserEntity.setTokenPassword(null);
      oUserRepository.save(oUserEntity);

      return new ResponseEntity(null, HttpStatus.OK);
    
    }
}
