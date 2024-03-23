package com.example.manasfen.services.photo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NewsPhotoDimensionHolder implements PhotoDimensionHolder{

    @Value("${custom.parameters.news.photo.width}")
    private int maxWidth;
    @Value("${custom.parameters.news.photo.height}")
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
