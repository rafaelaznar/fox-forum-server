package net.ausiasmarch.foxforumserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "captcha")
public class CaptchaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    private String text;

    @Lob
    private byte[] image;

    public CaptchaEntity() {
    }

    public CaptchaEntity(Long id, String text, byte[] image) {
        this.id = id;
        this.text = text;
        this.image = image;
    }

    public CaptchaEntity(String text, byte[] image) {
        this.text = text;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;        
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
}
