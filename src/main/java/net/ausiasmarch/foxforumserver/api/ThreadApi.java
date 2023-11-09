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

import net.ausiasmarch.foxforumserver.entity.ThreadEntity;
import net.ausiasmarch.foxforumserver.service.ThreadService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/thread")
public class ThreadApi {
    @Autowired
    ThreadService oThreadService;

    @GetMapping("/{id}")
    public ResponseEntity<ThreadEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oThreadService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody ThreadEntity oThreadEntity) {
        return ResponseEntity.ok(oThreadService.create(oThreadEntity));
    }

    @PutMapping("")
    public ResponseEntity<ThreadEntity> update(@RequestBody ThreadEntity oThreadEntity) {
        return ResponseEntity.ok(oThreadService.update(oThreadEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oThreadService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<ThreadEntity>> getPage(Pageable oPageable,
            @RequestParam(value = "user", defaultValue = "0", required = false) Long userId) {
        return ResponseEntity.ok(oThreadService.getPage(oPageable, userId));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(oThreadService.populate(amount));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oThreadService.empty());
    }

    @GetMapping("/byRepliesNumberDesc")
    public ResponseEntity<Page<ThreadEntity>> getPageByRepliesNumberDesc(Pageable oPageable,
            @RequestParam(value = "user", defaultValue = "0", required = false) Long userId) {
        return ResponseEntity.ok(oThreadService.getPageByRepliesNumberDesc(oPageable, userId));
    }

}
