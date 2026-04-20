package com.krev.musicloader.api.dto;

import com.krev.musicloader.api.dto.SearchDto.Tracks;
import lombok.Data;

@Data
public class SpotifySearchDto {
    private Tracks tracks;
}

