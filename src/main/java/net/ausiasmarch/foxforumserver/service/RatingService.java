package net.ausiasmarch.foxforumserver.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.foxforumserver.entity.RatingEntity;
import net.ausiasmarch.foxforumserver.entity.ReplyEntity;
import net.ausiasmarch.foxforumserver.entity.UserEntity;
import net.ausiasmarch.foxforumserver.exception.ResourceNotFoundException;
import net.ausiasmarch.foxforumserver.repository.RatingRepository;

@Service
public class RatingService {

    @Autowired
    RatingRepository oRatingRepository;
    @Autowired
    private UserService oUserService;
    @Autowired
    private ReplyService oReplyService;
    @Autowired
    SessionService oSessionService;
    @Autowired
    HttpServletRequest oHttpServletRequest;

    // Get a single rating by id
    public RatingEntity get(Long id) {
        return oRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found"));
    }

    public Page<RatingEntity> getPage(Pageable oPageable) {
        oSessionService.onlyAdminsOrUsers();
        return oRatingRepository.findAll(oPageable);
    }

    // Get all ratings
    public List<RatingEntity> getAllIds() {
        return oRatingRepository.findAll();
    }

    // Create a new rating
    public Long create(RatingEntity oRatingEntity) {
        oSessionService.onlyAdminsOrUsers();
        oRatingEntity.setId(null);
        oRatingEntity.setCreated_at(LocalDateTime.now());
        if (oSessionService.isUser()) {
            oRatingEntity.setUser(oSessionService.getSessionUser());
            return oRatingRepository.save(oRatingEntity).getId();
        } else {
            if (oRatingEntity.getUser().getId() == null || oRatingEntity.getUser().getId() == 0) {
                oRatingEntity.setUser(oSessionService.getSessionUser());
            }
            return oRatingRepository.save(oRatingEntity).getId();
        }
    }

    // Update an existing rating
    public RatingEntity update(RatingEntity oRatingEntityToSet) {
        RatingEntity oRatingEntityFromDatabase = this.get(oRatingEntityToSet.getId());
        oSessionService.onlyAdminsOrUsersWithIisOwnData(oRatingEntityFromDatabase.getUser().getId());
        if (oSessionService.isUser()) {
            oRatingEntityToSet.setUser(oSessionService.getSessionUser());
            return oRatingRepository.save(oRatingEntityToSet);
        } else {
            return oRatingRepository.save(oRatingEntityToSet);
        }
    }

    // Delete a rating by id
    public Long delete(Long id) {
        RatingEntity oRatingEntityFromDatabase = this.get(id);
        oSessionService.onlyAdminsOrUsersWithIisOwnData(oRatingEntityFromDatabase.getUser().getId());
        oRatingRepository.deleteById(id);
        return id;
    }

    // Populate the database with random data
    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            UserEntity randomUser = oUserService.getOneRandom();
            ReplyEntity randomReply = oReplyService.getOneRandom();
            int randomStars = ThreadLocalRandom.current().nextInt(1, 6);
            oRatingRepository
                    .save(new RatingEntity(randomUser, randomReply, randomStars, LocalDateTime.now()));
        }
        return oRatingRepository.count();
    }

    // Delete all ratings
    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oRatingRepository.deleteAll();
        oRatingRepository.resetAutoIncrement();
        oRatingRepository.flush();
        return oRatingRepository.count();
    }

    // Create a new rating for a reply checking first if there is already a rating
    // in case of scalation
    @Transactional
    public RatingEntity rateReply(RatingEntity oRatingEntity) {
        // Comprueba si ya existe una valoración para el usuario de la sesión y el reply
        Optional<RatingEntity> existingRating = oRatingRepository.findByUserAndReply(
                oRatingEntity.getUser(), oRatingEntity.getReply());

        if (existingRating.isPresent()) {
            RatingEntity oldRating = existingRating.get();
            oldRating.setStars(oRatingEntity.getStars());
            oldRating.setCreated_at(LocalDateTime.now());
            return oRatingRepository.save(oldRating);
        } else {
            return oRatingRepository.save(oRatingEntity);
        }
    }

    // Método para calcular la media de valoraciones para todas las respuestas
    public Map<Long, Double> calculateAverageRatingForAllReplies() {
        // Obtén todas las valoraciones con findAll
        List<RatingEntity> allRatings = oRatingRepository.findAll();
        // Agrupa las valoraciones por id_reply usando una colleción map
        // las claves son los IDs y los valores del map los valores de cada valoración
        // asociada a cada ID
        Map<Long, List<RatingEntity>> ratingsByReplyId = allRatings.stream()
                .collect(Collectors.groupingBy(rating -> rating.getReply().getId()));
        // Calcula la media para cada id_reply usando el map iterando por cada entrada
        // con un forEach sumando los valores de los datos guardados en el campo stars y
        // dividiendo por el número de valoraciones para sacar la media
        Map<Long, Double> averageRatingsByReplyId = new HashMap<>();
        ratingsByReplyId.forEach((replyId, ratings) -> {
            if (!ratings.isEmpty()) {
                int totalStars = ratings.stream()
                        .mapToInt(RatingEntity::getStars)
                        .sum();
                double averageRating = (double) totalStars / ratings.size();
                averageRatingsByReplyId.put(replyId, averageRating);
            }
        });
        // Devuelve un mapa donde las claves son los IDs de respuesta
        // y los valores son las medias de las valoraciones para esas respuestas
        return averageRatingsByReplyId;
    }

    // Método para contar las valoraciones para todas las respuestas
    public Map<Long, Integer> countRatingsForAllReplies() {
        // Obtiene todas las valoraciones con findAll y las guarda en una lista de
        // objetos ratingEntity
        List<RatingEntity> allRatings = oRatingRepository.findAll();
        // Usando map agrupa las valoraciones por id_reply y cuenta la cantidad de votos
        // por grupo usando una colleción map: las claves son los IDs y los valores del
        // map los valores de cada valoración

        // .stream() toma la lista y la convierte en un flujo (stream) de elementos
        // GroupingBy agrupa las valoraciones por el ID y summingInt() suma los valores
        // de cada valoración usando una función de acumulación aplicada a cada elemento

        // Collectors.summingInt(rating -> 1)
        // Para cada valoracion se asigna el valor que sirve como constante.
        // De este modo sumamos esos valores para todos usando la constante
        // dando como resultado la cantidad total de elementos (cantidad total de
        // valoraciones para ese ID
        // reply concreto)
        Map<Long, Integer> countByReplyId = allRatings.stream()
                .collect(
                        Collectors.groupingBy(rating -> rating.getReply().getId(), Collectors.summingInt(rating -> 1)));
        // Devuelve un mapa donde las claves son los IDs de respuesta
        // y los valores son la cantidad de valoraciones para esas respuestas
        return countByReplyId;
    }

    /*
     * // Método para obtener las valoraciones asociadas a respuestas específicas
     * public List<RatingEntity> getRatingsByReplyIds(List<Long> replyIds) {
     * return oRatingRepository.findByReplyIdIn(replyIds);
     * }
     */

}
