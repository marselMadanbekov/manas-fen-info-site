package com.example.manasfen.model.entyties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsefulLink extends ParentEntity{
    @Column(nullable = false)
    private String title;
    private String subtitle;

    @Column(nullable = false, length = 16000)
    private String text;

    @Column(nullable = false)
    private String photo;

    public String getDateMonth(){
        Locale russianLocale = new Locale("ru");

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", russianLocale);
        return sdf.format(super.getCreateDate());
    }
    public String getDateDay(){
        Locale russianLocale = new Locale("ru");

        SimpleDateFormat sdf = new SimpleDateFormat("dd", russianLocale);
        return sdf.format(super.getCreateDate());
    }
}
