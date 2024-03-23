package com.example.manasfen.model.entyties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News extends ParentEntity {

    private Date eventDate;

    @Column(nullable = false)
    private String title;
    private String subtitle;
    @Column(nullable = false,length = 160000)
    private String text;

    @Column(nullable = false)
    private String photo;

    @Override
    public Long getId(){
        return super.getId();
    }
    @Override
    public Date getCreateDate(){
        return super.getCreateDate();
    }
    @Override
    public Date getLastUpdate(){
        return super.getLastUpdate();
    }

    public String getDateMonth(){
        Locale russianLocale = new Locale("ru");

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", russianLocale);
        return sdf.format(eventDate);
    }
    public String getDateDay(){
        Locale russianLocale = new Locale("ru");

        SimpleDateFormat sdf = new SimpleDateFormat("dd", russianLocale);
        return sdf.format(eventDate);
    }

}
