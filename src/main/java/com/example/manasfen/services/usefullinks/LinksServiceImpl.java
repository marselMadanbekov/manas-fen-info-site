package com.example.manasfen.services.usefullinks;


import com.example.manasfen.exceptions.InvalidDataFormatException;
import com.example.manasfen.model.entyties.UsefulLink;
import com.example.manasfen.model.payload.LinkCreate;
import com.example.manasfen.reposiroties.UsefulLinkRepository;
import com.example.manasfen.services.photo.LinksPhotoDimensionHolder;
import com.example.manasfen.services.photo.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LinksServiceImpl implements LinksService {

    @Value("${custom.parameters.links.page-size}")
    private Integer pageSize;
    private final LinksPhotoDimensionHolder linksPhotoDimensionHolder;

    private final PhotoService photoService;

    private final UsefulLinkRepository linksRepository;

    @Override
    public Page<UsefulLink> findAllByPage(Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return linksRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public UsefulLink createLink(LinkCreate linkCreate) throws IOException, InvalidDataFormatException {
        String photo = photoService.savePhoto(linkCreate.photo(),linksPhotoDimensionHolder);

        return linksRepository.save(
                UsefulLink.builder()
                        .title(linkCreate.title())
                        .subtitle(linkCreate.subtitle())
                        .text(linkCreate.text())
                        .photo(photo)
                        .build()
        );
    }

    @Override
    public List<UsefulLink> getLastLinks() {
        return linksRepository.findFirst3ByOrderByCreateDateDesc();
    }

    @Override
    public UsefulLink findById(Long linkId) {
        return linksRepository.findById(linkId).orElseThrow(() -> new NoSuchElementException("Маалымат табылган жок!"));
    }
}
