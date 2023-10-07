package net.ausiasmarch.foxforumserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reply")
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    String title;
    String body;

    public ReplyEntity() {
    }

    public ReplyEntity(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public ReplyEntity(Long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
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
    
}
