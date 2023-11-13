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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.ausiasmarch.foxforumserver.entity.ReplyEntity;
import net.ausiasmarch.foxforumserver.service.ReplyService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/reply")
public class ReplyApi {
    @Autowired
    ReplyService oReplyService;

    @GetMapping("/{id}")
    public ResponseEntity<ReplyEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oReplyService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody ReplyEntity oReplyEntity) {
        return ResponseEntity.ok(oReplyService.create(oReplyEntity));
    }

    @PutMapping("")
    public ResponseEntity<ReplyEntity> update(@RequestBody ReplyEntity oReplyEntity) {
        return ResponseEntity.ok(oReplyService.update(oReplyEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oReplyService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<ReplyEntity>> getPage(Pageable oPageable,
            @RequestParam(value = "user", defaultValue = "0", required = false) Long userId,
            @RequestParam(value = "thread", defaultValue = "0", required = false) Long threadId) {
        return ResponseEntity.ok(oReplyService.getPage(oPageable, userId, threadId));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(oReplyService.populate(amount));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oReplyService.empty());
    }
}
