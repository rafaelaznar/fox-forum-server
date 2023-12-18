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

    /**
     * Retrieves a rating entity by its identifier.
     *
     * @param id The identifier of the rating.
     * @return ResponseEntity containing the retrieved rating entity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RatingEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oRatingService.get(id));
    }

    /**
     * Creates a new rating entity.
     *
     * @param oRatingEntity The rating entity to be created.
     * @return ResponseEntity containing the identifier of the created rating
     *         entity.
     */
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody RatingEntity oRatingEntity) {
        return ResponseEntity.ok(oRatingService.create(oRatingEntity));
    }

    /**
     * Updates an existing rating entity.
     *
     * @param oRatingEntity The updated rating entity.
     * @return ResponseEntity containing the updated rating entity.
     */
    @PutMapping("")
    public ResponseEntity<RatingEntity> update(@RequestBody RatingEntity oRatingEntity) {
        return ResponseEntity.ok(oRatingService.update(oRatingEntity));
    }

    /**
     * Deletes a rating entity by its identifier.
     *
     * @param id The identifier of the rating to be deleted.
     * @return ResponseEntity containing the identifier of the deleted rating
     *         entity.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oRatingService.delete(id));
    }

    /**
     * Retrieves a page of rating entities based on the provided parameters.
     *
     * @param oPageable Pageable object for pagination.
     * @param userId    The identifier of the user (optional, default is 0).
     * @param threadId  The identifier of the thread (optional, default is 0).
     * @param replyId   The identifier of the reply (optional, default is 0).
     * @return ResponseEntity containing the page of retrieved rating entities.
     */
    @GetMapping("")
    public ResponseEntity<Page<RatingEntity>> getPage(Pageable oPageable,
            @RequestParam(value = "user", defaultValue = "0", required = false) Long userId,
            @RequestParam(value = "thread", defaultValue = "0", required = false) Long threadId,
            @RequestParam(value = "reply", defaultValue = "0", required = false) Long replyId) {
        return ResponseEntity.ok(oRatingService.getPage(oPageable, userId, threadId, replyId));
    }

    /**
     * Populates the database with a specified number of rating entities.
     *
     * @param amount The number of rating entities to populate.
     * @return ResponseEntity containing the number of rating entities populated.
     */
    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(oRatingService.populate(amount));
    }

    /**
     * Deletes all rating entities in the database.
     *
     * @return ResponseEntity indicating the success of the operation.
     */
    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oRatingService.empty());
    }

    /**
     * Endpoint to submit a star rating for a reply.
     *
     * @param rating The RatingEntity object containing the details of the rating.
     * @return ResponseEntity with the saved RatingEntity and HTTP status OK if
     *         successful.
     */
    @PostMapping("/rateReply")
    public ResponseEntity<RatingEntity> rateReply(@RequestBody RatingEntity rating) {
        // Add logic to handle the rating
        RatingEntity savedRating = oRatingService.rateReply(rating);
        return new ResponseEntity<>(savedRating, HttpStatus.OK);
    }

    /*
     * 1. Anotación @GetMapping("/average/all"): Define que este método responderá a
     * las solicitudes HTTP GET en la ruta "/average/all".
     * 
     * 2. Retorno de tipo ResponseEntity<Map<Long, Double>>: El método devuelve un
     * objeto ResponseEntity que envuelve un Map con claves de tipo Long
     * (identificadores de respuestas) y valores de tipo Double (medias de
     * valoración).
     * 
     * 3. Llamada a oRatingService.calculateAverageRatingForAllReplies(): Invoca al
     * método calculateAverageRatingForAllReplies() del servicio oRatingService.
     * Este método está destinado a calcular la media de las valoraciones para todas
     * las respuestas y devuelve un Map con las medias asociadas a los
     * identificadores de las respuestas.
     * 
     * 4. ResponseEntity.ok(averageRatingsByReplyId): Construye un objeto
     * ResponseEntity con el Map de medias obtenido del servicio. Esto indica que la
     * solicitud fue exitosa y contiene el cuerpo de la respuesta, que es el Map de
     * medias.
     * 
     * 
     */

    /**
     * Endpoint to retrieve the average ratings for all replies.
     *
     * @return ResponseEntity with a Map containing reply IDs as keys and their
     *         corresponding average ratings as values.
     */
    @GetMapping("/average/all")
    public ResponseEntity<Map<Long, Double>> getAverageRatingForAllReplies() {
        Map<Long, Double> averageRatingsByReplyId = oRatingService.calculateAverageRatingForAllReplies();
        return ResponseEntity.ok(averageRatingsByReplyId);
    }

    /**
     * Endpoint to retrieve the count of ratings for all replies.
     *
     * @return ResponseEntity with a Map containing reply IDs as keys and the count
     *         of ratings for each reply as values.
     */
    @GetMapping("/count/all")
    public ResponseEntity<Map<Long, Integer>> countRatingsForAllReplies() {
        Map<Long, Integer> countByReplyId = oRatingService.countRatingsForAllReplies();
        return ResponseEntity.ok(countByReplyId);
    }

    @GetMapping("/allIds")
    public ResponseEntity<List<RatingEntity>> getAllIds() {
        List<RatingEntity> allRatings = oRatingService.getAllIds();
        return new ResponseEntity<>(allRatings, HttpStatus.OK);
    }

}