package com.krev.musicloader.music;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/music")
public class MusicController {
    @PostMapping
    public ResponseEntity<MusicEntity> create(@RequestBody @Valid CreateMusicDTO dto) {
        MusicEntity result = MusicService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
