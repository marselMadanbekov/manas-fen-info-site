package com.example.manasfen.model.entyties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher extends ParentEntity {
    @Column(nullable = false, length = 16000)
    private String info;
    private String degree;
    private String photo;
    @OneToOne
    private User userInfo;

    public String fullName() {
        return userInfo.getFirstname() + " " + userInfo.getLastname();
    }
}
