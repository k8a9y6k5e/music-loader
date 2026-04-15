package com.krev.musicloader.music;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;

    public MusicEntity create(CreateMusicDTO dto) {
        MusicEntity music = new MusicEntity();

        music.setName(dto.getName());
        music.setArtist(dto.getArtist());
        music.setUrl(dto.getUrl());

        return musicRepository.save(music);
    }
}
