package com.example.manasfen.services.usefullinks;

import com.example.manasfen.exceptions.InvalidDataFormatException;
import com.example.manasfen.model.entyties.UsefulLink;
import com.example.manasfen.model.payload.LinkCreate;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface LinksService {
    Page<UsefulLink> findAllByPage(Integer page);

    UsefulLink findById(Long linksId);

    UsefulLink createLink(LinkCreate linksCreate) throws IOException, InvalidDataFormatException;

    List<UsefulLink> getLastLinks();

    void deleteById(Long surveyId);
}
