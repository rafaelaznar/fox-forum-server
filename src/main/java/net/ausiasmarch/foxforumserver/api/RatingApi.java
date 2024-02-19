package net.ausiasmarch.foxforumserver.api;

import java.util.List;
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

    @GetMapping("")
    public ResponseEntity<Page<RatingEntity>> getPage(Pageable oPageable) {
        return ResponseEntity.ok(oRatingService.getPage(oPageable));
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

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(oRatingService.populate(amount));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oRatingService.empty());
    }

    @PostMapping("/rateReply")
    public ResponseEntity<RatingEntity> rateReply(@RequestBody RatingEntity rating) {
        RatingEntity savedRating = oRatingService.rateReply(rating);
        return new ResponseEntity<>(savedRating, HttpStatus.OK);
    }

    @GetMapping("/average/all")
    public ResponseEntity<Map<Long, Double>> getAverageRatingForAllReplies() {
        Map<Long, Double> averageRatingsByReplyId = oRatingService.calculateAverageRatingForAllReplies();
        return ResponseEntity.ok(averageRatingsByReplyId);
    }

    @GetMapping("/count/all")
    public ResponseEntity<Map<Long, Integer>> countRatingsForAllReplies() {
        Map<Long, Integer> countByReplyId = oRatingService.countRatingsForAllReplies();
        return ResponseEntity.ok(countByReplyId);
    }
/*    @GetMapping("/allIds")
    public ResponseEntity<List<RatingEntity>> getAllIds() {
        List<RatingEntity> allRatings = oRatingService.getAllIds();
        return new ResponseEntity<>(allRatings, HttpStatus.OK);
    }
    */

}