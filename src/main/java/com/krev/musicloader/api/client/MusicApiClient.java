package com.krev.musicloader.api.client;

import com.krev.musicloader.api.service.dto.MusicSearchDto;

import java.util.List;

public interface MusicApiClient {
    public MusicSearchDto searchMusic(String name, Integer track);

    public List<MusicSearchDto> listMusic(String name);
}
