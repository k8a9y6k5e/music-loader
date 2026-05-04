package com.krev.musicloader.music;
import com.krev.musicloader.api.service.dto.MusicSearchDto;
import com.krev.musicloader.api.service.MusicSearchService;
import com.krev.musicloader.exception.NotFoundException;
import com.krev.musicloader.music.dto.CreateMusicDto;
import com.krev.musicloader.music.dto.ListSearchedMusicDto;
import com.krev.musicloader.music.dto.PatchUpdateMusicDto;
import com.krev.musicloader.music.dto.PutUpdateMusicDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.lang.Long;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private MusicSearchService musicSearchService;

    public MusicEntity create(CreateMusicDto dto) {
        MusicEntity music = new MusicEntity();

        MusicSearchDto musicSearch = musicSearchService.search(dto.getName(), dto.getTrack());

        music.setName(musicSearch.name());

        music.setUrl(musicSearch.url());

        music.setArtist(musicSearch.artists());

        return musicRepository.save(music);
    }

    public List<MusicSearchDto> listMusicSearched (ListSearchedMusicDto dto) {
        return musicSearchService.list(dto.getName());
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

        MusicSearchDto searchResponse = musicSearchService.search(dto.getName(), dto.getTrack());

        result.setName(searchResponse.name());

        result.setUrl(searchResponse.url());

        result.setArtist(searchResponse.artists());

        return musicRepository.save(result);
    }

    public MusicEntity patchUpdate (Long id, PatchUpdateMusicDto dto) {
        MusicEntity result = search(id);

        if(dto.getName() != null)
            result.setName(dto.getName());

        if(dto.getResearch()) {
            MusicSearchDto searchResponse = musicSearchService.search(dto.getName(), dto.getTrack());

            result.setUrl(searchResponse.url());

            result.setArtist(searchResponse.artists());
        }

        return musicRepository.save(result);
    }
}
