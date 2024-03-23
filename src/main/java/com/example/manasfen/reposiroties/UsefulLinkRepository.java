package com.example.manasfen.reposiroties;

import com.example.manasfen.model.entyties.UsefulLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsefulLinkRepository extends JpaRepository<UsefulLink,Long> {
    List<UsefulLink> findFirst3ByOrderByCreateDateDesc();
}
