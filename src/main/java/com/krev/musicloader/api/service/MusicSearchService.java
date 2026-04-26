package com.krev.musicloader.api.service;

import com.krev.musicloader.api.client.MusicApiClient;
import com.krev.musicloader.api.repository.MusicSearchEntity;
import com.krev.musicloader.api.repository.MusicSearchRepository;
import com.krev.musicloader.api.invidious.InvidiousClient;
import com.krev.musicloader.api.service.dto.MusicSearchDto;
import com.krev.musicloader.api.spotify.SpotifyClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MusicSearchService {
    @Autowired
    private MusicSearchRepository orchestratorRepository;

    @Autowired
    private SpotifyClient spotifyClient;

    @Autowired
    private InvidiousClient invidiousClient;

    private Map<String, MusicApiClient> adapters = new HashMap<>();

    @PostConstruct
    private void initAdaptersMap() {
        adapters.put("client1", spotifyClient);
        adapters.put("client2", invidiousClient);
    }

    public MusicSearchDto callApi(String name) {
        Exception lastException = null;

        for(MusicSearchEntity row : orchestratorRepository.findByActivityTrue(Sort.by("priority"))) {
            try {
                MusicApiClient api = adapters.get(row.getName());

                return api.searchMusic(name);
            }
            catch (Exception err) {
                lastException = err;
            }
        }

        throw new RuntimeException("None API found the music entered", lastException);
    }
}
