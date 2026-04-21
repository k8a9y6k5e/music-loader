package com.krev.musicloader.music;
import com.krev.musicloader.api.dto.SearchDto.Artists;
import com.krev.musicloader.exception.NotFoundException;
import com.krev.musicloader.music.dto.CreateMusicDto;
import com.krev.musicloader.music.dto.PatchUpdateMusicDto;
import com.krev.musicloader.music.dto.PutUpdateMusicDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.krev.musicloader.api.SpotifyClient;
import com.krev.musicloader.api.dto.SpotifySearchDto;
import org.springframework.data.domain.Pageable;
import java.util.*;
import java.lang.Long;

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

        music.setUrl(getUrl(musicSearch, 0));

        music.setArtist(getArtists(musicSearch, 0));

        music.setSearchIndex(0);

        return musicRepository.save(music);
    }

    private String getUrl(SpotifySearchDto musicSearched, Integer track) {
        return musicSearched.getTracks().getItems().get(track).getExternal_urls().getSpotify();
    }

    private String getArtists(SpotifySearchDto musicSearched, Integer track) {
        List<String> artistsNames = new ArrayList<>();

        for (Artists artists : musicSearched.getTracks().getItems().get(track).getArtists()) {
            artistsNames.add(artists.getName());
        }

        return String.join(", ", artistsNames);
    }

    public Page<MusicEntity> list(Pageable pageable) {
        return musicRepository.findAll(pageable);
    }

    public MusicEntity search(Long id) {
        return musicRepository.findById(id).orElseThrow(() -> new NotFoundException("Music not founded"));//change there
    }

    public void delete(Long id) {
        musicRepository.deleteById(id);
    }

    public MusicEntity putUpdate (Long id, PutUpdateMusicDto dto) {
        MusicEntity result = search(id);

        SpotifySearchDto search = spotifyClient.searchMusic(dto.getName());

        result.setName(dto.getName());

        result.setUrl(getUrl(search, 0));

        result.setArtist(getArtists(search, 0));

        return musicRepository.save(result);
    }

    public MusicEntity patchUpdate (Long id, PatchUpdateMusicDto dto) {
        MusicEntity result = search(id);

        if(dto.getName() != null)
            result.setName(dto.getName());

        if(dto.getResearch()) {
            SpotifySearchDto search = spotifyClient.searchMusic(dto.getName());

            result.setUrl(getUrl(search, 0));

            result.setArtist(getArtists(search, 0));
        }

        return musicRepository.save(result);
    }

    public MusicEntity nextMusicToSave (Long id) {
        MusicEntity result = search(id);

        SpotifySearchDto search = spotifyClient.searchMusic(result.getName());

        Integer searchQuantity = search.getTracks().getItems().toArray().length;

        if(result.getSearchIndex() == searchQuantity){
            result.setSearchIndex(0);
        }

        result.setUrl(getUrl(search, result.getSearchIndex()));

        result.setArtist(getArtists(search, result.getSearchIndex()));

        result.setSearchIndex(result.getSearchIndex()+1);

        return musicRepository.save(result);
    }
}
