package com.example.manasfen.model.entyties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentsArticle extends ParentEntity{

    @Column(nullable = false)
    private String fullName;
    private String subtitle;
    @Column(nullable = false,length = 16000)
    private String story;

    @Column(nullable = false)
    private String photo;
}
