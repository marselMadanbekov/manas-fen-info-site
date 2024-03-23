package com.example.manasfen.reposiroties;

import com.example.manasfen.model.entyties.StudentsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentArticleRepository extends JpaRepository<StudentsArticle,Long> {
    List<StudentsArticle> findFirst3ByOrderByCreateDateDesc();
}
