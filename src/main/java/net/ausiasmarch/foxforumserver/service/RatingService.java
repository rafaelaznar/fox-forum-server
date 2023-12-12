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
import net.ausiasmarch.foxforumserver.entity.ThreadEntity;
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
    private ThreadService oThreadService;

    @Autowired
    private ReplyService oReplyService;

    @Autowired
    SessionService oSessionService;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    public RatingEntity get(Long id) {
        return oRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found"));
    }

    public Page<RatingEntity> getPage(Pageable pageable, Long userId, Long threadId, Long replyId) {
        if (userId != null && userId > 0) {
            return oRatingRepository.findByUserId(userId, pageable);
        } else if (threadId != null && threadId > 0) {
            return oRatingRepository.findByThreadId(threadId, pageable);
        } else if (replyId != null && replyId > 0) {
            return oRatingRepository.findByReplyId(replyId, pageable);
        } else {
            return oRatingRepository.findAll(pageable);
        }
    }

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

    public RatingEntity update(RatingEntity oRatingEntityToSet) {
        RatingEntity oRatingEntityFromDatabase = this.get(oRatingEntityToSet.getId());
        oSessionService.onlyAdminsOrUsersWithIisOwnData(oRatingEntityFromDatabase.getUser().getId());
        oRatingEntityToSet.setCreated_at(LocalDateTime.now());
        if (oSessionService.isUser()) {
            oRatingEntityToSet.setUser(oSessionService.getSessionUser());
            return oRatingRepository.save(oRatingEntityToSet);
        } else {
            return oRatingRepository.save(oRatingEntityToSet);
        }
    }

    public Long delete(Long id) {
        RatingEntity oRatingEntityFromDatabase = this.get(id);
        oSessionService.onlyAdminsOrUsersWithIisOwnData(oRatingEntityFromDatabase.getUser().getId());
        oRatingRepository.deleteById(id);
        return id;
    }

    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            UserEntity randomUser = oUserService.getOneRandom();
            ThreadEntity randomThread = oThreadService.getOneRandom();
            ReplyEntity randomReply = oReplyService.getOneRandom();
            int randomStars = ThreadLocalRandom.current().nextInt(1, 6);
            oRatingRepository
                    .save(new RatingEntity(randomUser, randomThread, randomReply, randomStars, LocalDateTime.now()));
        }
        return oRatingRepository.count();
    }

    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oRatingRepository.deleteAll();
        oRatingRepository.resetAutoIncrement();
        oRatingRepository.flush();
        return oRatingRepository.count();
    }

    @Transactional
    public RatingEntity rateReply(RatingEntity oRatingEntity) {
        // Verificar si ya existe una valoración del mismo usuario para el mismo hilo o
        // respuesta
        Optional<RatingEntity> existingRating;

        if (oRatingEntity.getThread() != null) {
            existingRating = oRatingRepository.findByUserAndThreadAndReply(
                    oRatingEntity.getUser(), oRatingEntity.getThread(), oRatingEntity.getReply());
        } else if (oRatingEntity.getReply() != null) {
            existingRating = oRatingRepository.findByUserAndReply(oRatingEntity.getUser(), oRatingEntity.getReply());
        } else {
            // Manejo de error o lógica adicional si es necesario
            throw new IllegalArgumentException("Thread or Reply must be provided for rating");
        }

        if (existingRating.isPresent()) {
            // Si ya existe una valoración, actualiza las estrellas y la fecha de creación
            RatingEntity oldRating = existingRating.get();
            oldRating.setStars(oRatingEntity.getStars());
            oldRating.setCreated_at(LocalDateTime.now()); // Actualiza la fecha de creación si es necesario
            return oRatingRepository.save(oldRating);
        } else {
            // Si no existe una valoración, guarda la nueva valoración
            return oRatingRepository.save(oRatingEntity);
        }
    }

    public Map<Long, Double> calculateAverageRatingForAllReplies() {
        // Obtén todas las valoraciones
        List<RatingEntity> allRatings = oRatingRepository.findAll();

        // Agrupa las valoraciones por id_reply
        Map<Long, List<RatingEntity>> ratingsByReplyId = allRatings.stream()
                .collect(Collectors.groupingBy(rating -> rating.getReply().getId()));

        // Calcula la media para cada id_reply
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

        return averageRatingsByReplyId;
    }

    public Map<Long, Integer> countRatingsForAllReplies() {
        // Obtén todas las valoraciones
        List<RatingEntity> allRatings = oRatingRepository.findAll();

        // Agrupa las valoraciones por id_reply y cuenta la cantidad de votos por grupo
        Map<Long, Integer> countByReplyId = allRatings.stream()
                .collect(
                        Collectors.groupingBy(rating -> rating.getReply().getId(), Collectors.summingInt(rating -> 1)));

        return countByReplyId;
    }

}
