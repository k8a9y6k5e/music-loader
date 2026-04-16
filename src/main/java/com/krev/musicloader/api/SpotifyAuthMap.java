package com.krev.musicloader.api;

import lombok.Data;

@Data
public class SpotifyAuthMap {
    private String access_token;
    private String token_type;
    private int expires_in;
}
