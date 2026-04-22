package com.krev.musicloader.api.spotify.dto;

import lombok.Data;

@Data
public class SpotifyAuthDto {
    private String access_token;
    private String token_type;
    private int expires_in;
}
