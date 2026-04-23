package com.krev.musicloader.api;

import lombok.Data;

@Data
public class MusicSearchDto {
    private String url;
    private String artists;

    public MusicSearchDto(String url, String artists) {
        this.url = url;
        this.artists = artists;
    }
}
