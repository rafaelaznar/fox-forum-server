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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "reply")
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 2048)
    String title;

    @NotNull
    @NotBlank
    String body;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime creation;

    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "id_thread")
    private ThreadEntity thread;

    public ReplyEntity() {
    }

    public ReplyEntity(String title, String body, Boolean active) {
        this.title = title;
        this.body = body;
        this.active = active;
        this.creation = LocalDateTime.now();
    }

    public ReplyEntity(Long id, String title, String body, Boolean active) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.active = active;
        this.creation = LocalDateTime.now();
    }

    public ReplyEntity(String title, String body, Boolean active, LocalDateTime creation, UserEntity user,
            ThreadEntity thread) {
        this.title = title;
        this.body = body;
        this.active = active;
    }

    public ReplyEntity(String title, String body) {
        this.title = title;
        this.body = body;
        this.creation = LocalDateTime.now();
    }

    public ReplyEntity(Long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.creation = LocalDateTime.now();
    }

    public ReplyEntity(String title, String body, LocalDateTime creation, UserEntity user, ThreadEntity thread) {
        this.title = title;
        this.body = body;
        this.creation = creation;
        this.user = user;
        this.thread = thread;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ThreadEntity getThread() {
        return thread;
    }

    public void setThread(ThreadEntity thread) {
        this.thread = thread;
    }

    // Added by paula
    public LocalDateTime getCreation() {
        return creation;
    }

    public void setCreation(LocalDateTime creation) {
        this.creation = creation;
    }

}
