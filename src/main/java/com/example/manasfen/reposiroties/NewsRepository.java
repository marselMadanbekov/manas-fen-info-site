package com.example.manasfen.reposiroties;

import com.example.manasfen.model.entyties.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News,Long> {
    List<News> findFirst3ByOrderByCreateDateDesc();
}
