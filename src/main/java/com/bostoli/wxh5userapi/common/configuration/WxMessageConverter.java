package com.bostoli.wxh5userapi.common.configuration;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WxMessageConverter extends MappingJackson2HttpMessageConverter {
    public WxMessageConverter(){
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8));
        mediaTypes.add(MediaType.TEXT_HTML);
        setSupportedMediaTypes(mediaTypes);
    }
}
