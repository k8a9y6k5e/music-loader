package com.krev.musicloader.music;
import com.krev.musicloader.api.dto.SearchDto.Artists;
import com.krev.musicloader.music.dto.CreateMusicDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.krev.musicloader.api.SpotifyClient;
import com.krev.musicloader.api.dto.SpotifySearchDto;
import org.springframework.data.domain.Pageable;
import java.util.*;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private SpotifyClient spotifyClient;

    public MusicEntity create(CreateMusicDto dto) {
        MusicEntity music = new MusicEntity();

        SpotifySearchDto musicSearch = spotifyClient.searchMusic(dto.getName());

        music.setName(dto.getName());

        music.setUrl(getUrl(musicSearch));

        music.setArtist(getArtists(musicSearch));

        return musicRepository.save(music);
    }

    private String getUrl(SpotifySearchDto musicSearched) {
        return musicSearched.getTracks().getItems().get(0).getExternal_urls().getSpotify();
    }

    private String getArtists(SpotifySearchDto musicSearched) {
        List<String> artistsNames = new ArrayList<>();

        for (Artists artists : musicSearched.getTracks().getItems().get(0).getArtists()) {
            artistsNames.add(artists.getName());
        }

        return String.join(", ", artistsNames);
    }

    public Page<MusicEntity> list(Pageable pageable) {
        return musicRepository.findAll(pageable);
    }
}
