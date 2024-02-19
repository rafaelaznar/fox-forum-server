package net.ausiasmarch.foxforumserver.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "rating")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_reply", nullable = false)
    private ReplyEntity reply;

    @Min(value = 1, message = "Stars cannot be less than 1")
    @Max(value = 5, message = "Stars cannot be greater than 5")
    private int stars;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime created_at;

    public RatingEntity() {
    }

    public RatingEntity(Long id, UserEntity user, ReplyEntity reply, int stars,
            @NotNull LocalDateTime created_at) {
        this.id = id;
        this.user = user;
        this.reply = reply;
        this.stars = stars;
        this.created_at = created_at;
    }

    public RatingEntity(UserEntity user, ReplyEntity reply, int stars,
            @NotNull LocalDateTime created_at) {
        this.user = user;
        this.reply = reply;
        this.stars = stars;
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ReplyEntity getReply() {
        return reply;
    }

    public void setReply(ReplyEntity reply) {
        this.reply = reply;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
