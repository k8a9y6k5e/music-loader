package com.krev.musicloader.api.spotify.dto;

import com.krev.musicloader.api.spotify.dto.SearchDto.Tracks;
import lombok.Data;

@Data
public class SpotifySearchDto {
    private Tracks tracks;
    private String error = null;
}

