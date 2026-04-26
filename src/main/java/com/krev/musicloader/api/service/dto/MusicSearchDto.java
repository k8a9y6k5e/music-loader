package com.krev.musicloader.api.service.dto;

import lombok.Data;

@Data
public class MusicSearchDto {
    private String url;
    private String artists;
    private String name;

    public MusicSearchDto(String url, String artists, String name) {
        this.url = url;
        this.artists = artists;
        this.name = name;
    }
}
