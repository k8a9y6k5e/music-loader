package com.krev.musicloader.music;
import com.krev.musicloader.music.dto.CreateMusicDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService service;

    @PostMapping
    public ResponseEntity<MusicEntity> create(@RequestBody @Valid CreateMusicDto dto) {
        MusicEntity result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<Page<MusicEntity>> list(Pageable pageable) {
        Page<MusicEntity> result = service.list(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
