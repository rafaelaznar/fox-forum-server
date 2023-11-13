package net.ausiasmarch.foxforumserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.ausiasmarch.foxforumserver.entity.UserEntity;
import net.ausiasmarch.foxforumserver.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    UserService oUserService;

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oUserService.get(id));
    }

    @GetMapping("/byUsername/{username}")
    public ResponseEntity<UserEntity> get(@PathVariable("username") String username) {
        return ResponseEntity.ok(oUserService.getByUsername(username));
    }


    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody UserEntity oUserEntity) {
        return ResponseEntity.ok(oUserService.create(oUserEntity));
    }

    @PutMapping("")
    public ResponseEntity<UserEntity> update(@RequestBody UserEntity oUserEntity) {
        return ResponseEntity.ok(oUserService.update(oUserEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oUserService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<UserEntity>> getPage(Pageable oPageable) {
        return ResponseEntity.ok(oUserService.getPage(oPageable));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(oUserService.populate(amount));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oUserService.empty());
    }

    @GetMapping("/byRepliesNumberDesc")
    public ResponseEntity<Page<UserEntity>> getPageByRepliesNumberDesc(Pageable oPageable) {
        return ResponseEntity.ok(oUserService.getPageByRepliesNumberDesc(oPageable));
    }

}
