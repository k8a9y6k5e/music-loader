package com.krev.musicloader.music;
import com.krev.musicloader.api.service.dto.MusicSearchDto;
import com.krev.musicloader.api.service.MusicSearchService;
import com.krev.musicloader.exception.NotFoundException;
import com.krev.musicloader.music.dto.CreateMusicDto;
import com.krev.musicloader.music.dto.PatchUpdateMusicDto;
import com.krev.musicloader.music.dto.PutUpdateMusicDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.lang.Long;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private MusicSearchService orchestrator;

    public MusicEntity create(CreateMusicDto dto) {
        MusicEntity music = new MusicEntity();

        MusicSearchDto musicSearch = orchestrator.callApi(dto.getName());

        music.setName(musicSearch.getName());

        music.setUrl(musicSearch.getUrl());

        music.setArtist(musicSearch.getArtists());

        music.setSearchIndex(0);

        return musicRepository.save(music);
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

        MusicSearchDto searchResponse = orchestrator.callApi(dto.getName());

        result.setName(dto.getName());

        result.setUrl(searchResponse.getUrl());

        result.setArtist(searchResponse.getArtists());

        return musicRepository.save(result);
    }

    public MusicEntity patchUpdate (Long id, PatchUpdateMusicDto dto) {
        MusicEntity result = search(id);

        if(dto.getName() != null)
            result.setName(dto.getName());

        if(dto.getResearch()) {
            MusicSearchDto searchResponse = orchestrator.callApi(dto.getName());

            result.setUrl(searchResponse.getUrl());

            result.setArtist(searchResponse.getArtists());
        }

        return musicRepository.save(result);
    }
}
