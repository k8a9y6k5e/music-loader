package com.krev.musicloader.music;
import com.krev.musicloader.music.dto.CreateMusicDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService service;

    @PostMapping
    public ResponseEntity<MusicEntity> create(@RequestBody @Valid CreateMusicDTO dto) {
        MusicEntity result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
