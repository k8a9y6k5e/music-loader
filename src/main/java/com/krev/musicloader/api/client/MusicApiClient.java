package com.krev.musicloader.api.client;

import com.krev.musicloader.api.service.dto.MusicSearchDto;

public interface MusicApiClient {
    public MusicSearchDto searchMusic(String name);
}
