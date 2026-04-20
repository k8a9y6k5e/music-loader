package com.krev.musicloader.music;
import com.krev.musicloader.api.dto.SearchDto.Artists;
import com.krev.musicloader.music.dto.CreateMusicDTO;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.krev.musicloader.api.SpotifyClient;
import com.krev.musicloader.api.dto.SpotifySearchDto;

import java.util.*;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private SpotifyClient spotifyClient;

    public MusicEntity create(CreateMusicDTO dto) {
        MusicEntity music = new MusicEntity();

        SpotifySearchDto musicSearch = spotifyClient.searchMusic(dto.getName());

        music.setName(dto.getName());

        music.setUrl(musicSearch.getTracks().getItems().get(0).getExternal_urls().getSpotify());

        List<String> artistsNames = new ArrayList<>();

        for (Artists artists : musicSearch.getTracks().getItems().get(0).getArtists()) {
            artistsNames.add(artists.getName());
        }

        music.setArtist(String.join(", ", artistsNames));

        return musicRepository.save(music);
    }
}
