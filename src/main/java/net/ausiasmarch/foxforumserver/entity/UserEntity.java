package net.ausiasmarch.foxforumserver.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 255)
    private String name;
    @NotNull
    @NotBlank
    @Size(min = 3, max = 255)
    private String surname;
    @Size(max = 255)
    private String lastname;
    @Email
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 6, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must be alphanumeric")
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @NotBlank
    @Size(min = 64, max = 64)
    @Pattern(regexp = "^[a-fA-F0-9]+$", message = "Password must be hexadecimal")
    private String password = "e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e";
    private String tokenPassword;

    private Boolean active = true;

    private boolean verified;

    private String token;

    private Boolean role = false;

    private Double latitude;
    
    private Double longitude;

    @OneToMany(mappedBy = "user", fetch = jakarta.persistence.FetchType.LAZY)
    private List<ThreadEntity> threads;

    @OneToMany(mappedBy = "user", fetch = jakarta.persistence.FetchType.LAZY)
    private List<ReplyEntity> replies;
    

    public UserEntity() {
        threads = new ArrayList<>();
        replies = new ArrayList<>();
    }

    public UserEntity(Long id, String name, String surname, String lastname, String email, String username,
            String password, Boolean role, Boolean active, boolean verified, String token) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
        this.verified = verified;
        this.token = token;
    }

    public UserEntity(Long id, String name, String surname, String lastname, String email, String username,
            String password, Boolean role, Boolean active) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public UserEntity(String name, String surname, String lastname, String email, String username, String password,
            Boolean role, Boolean active) {
    }

    public UserEntity(String name, String surname, String lastname, String email, String username, String password,
            Boolean role, Boolean active, boolean verified, String token) {
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;

    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenPassword() {
        return tokenPassword;
    }

    public void setTokenPassword(String tokenPassword) {
        this.tokenPassword = tokenPassword;
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getThreads() {
        return threads.size();
    }

    public int getReplies() {
        return replies.size();
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
