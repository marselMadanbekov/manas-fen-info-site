package com.example.manasfen.services.photo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ArticlesPhotoDimensionHolder implements PhotoDimensionHolder{

    @Value("${custom.parameters.articles.photo.width}")
    private int maxWidth;
    @Value("${custom.parameters.articles.photo.height}")
    private int maxHeight;

    @Override
    public Integer getMaxWidth() {
        return maxWidth;
    }

    @Override
    public Integer getMaxHeight() {
        return maxHeight;
    }
}
