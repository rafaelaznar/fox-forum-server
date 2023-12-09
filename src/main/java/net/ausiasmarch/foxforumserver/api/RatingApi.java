package net.ausiasmarch.foxforumserver.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import net.ausiasmarch.foxforumserver.entity.RatingEntity;
import net.ausiasmarch.foxforumserver.service.RatingService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/rating")
public class RatingApi {

    @Autowired
    RatingService oRatingService;

    @GetMapping("/{id}")
    public ResponseEntity<RatingEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oRatingService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody RatingEntity oRatingEntity) {
        return ResponseEntity.ok(oRatingService.create(oRatingEntity));
    }

    @PutMapping("")
    public ResponseEntity<RatingEntity> update(@RequestBody RatingEntity oRatingEntity) {
        return ResponseEntity.ok(oRatingService.update(oRatingEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oRatingService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<RatingEntity>> getPage(Pageable oPageable,
            @RequestParam(value = "user", defaultValue = "0", required = false) Long userId,
            @RequestParam(value = "thread", defaultValue = "0", required = false) Long threadId,
            @RequestParam(value = "reply", defaultValue = "0", required = false) Long replyId) {
        return ResponseEntity.ok(oRatingService.getPage(oPageable, userId, threadId, replyId));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(oRatingService.populate(amount));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oRatingService.empty());
    }

    // Valoración por estrellas
    @PostMapping("/rateReply")
    public ResponseEntity<RatingEntity> rateReply(@RequestBody RatingEntity rating) {
        // Agrega lógica para manejar la valoración
        RatingEntity savedRating = oRatingService.rateReply(rating);
        return new ResponseEntity<>(savedRating, HttpStatus.OK);
    }

    // En tu controlador de valoraciones (RatingController)
    @GetMapping("/average/all")
    public ResponseEntity<Map<Long, Double>> getAverageRatingForAllReplies() {
        Map<Long, Double> averageRatingsByReplyId = oRatingService.calculateAverageRatingForAllReplies();
        return ResponseEntity.ok(averageRatingsByReplyId);
    }
}
