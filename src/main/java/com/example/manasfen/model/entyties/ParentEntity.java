package com.example.manasfen.model.entyties;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@MappedSuperclass
@Data
public abstract class ParentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createDate;
    private Date lastUpdate;

    @PrePersist
    private void onCreate(){
        this.createDate = Date.valueOf(LocalDate.now());
        this.lastUpdate = Date.valueOf(LocalDate.now());
    }

    public void onUpdate(){
        this.lastUpdate = Date.valueOf(LocalDate.now());
    }
}
