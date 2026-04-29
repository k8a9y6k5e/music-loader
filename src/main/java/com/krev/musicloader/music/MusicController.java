package com.krev.musicloader.music;
import com.krev.musicloader.api.service.dto.MusicSearchDto;
import com.krev.musicloader.music.dto.CreateMusicDto;
import com.krev.musicloader.music.dto.ListSearchedMusicDto;
import com.krev.musicloader.music.dto.PatchUpdateMusicDto;
import com.krev.musicloader.music.dto.PutUpdateMusicDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

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
    public ResponseEntity<List<MusicSearchDto>> searchMusic(@ModelAttribute @Valid ListSearchedMusicDto dto) {
        List<MusicSearchDto> result = service.listMusicSearched(dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping
    public ResponseEntity<Page<MusicEntity>> list(Pageable pageable) {
        Page<MusicEntity> result = service.list(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicEntity> search(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.search(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicEntity> putUpdate(@PathVariable Long id, @RequestBody @Valid PutUpdateMusicDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.putUpdate(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MusicEntity> patchUpdate(@PathVariable Long id, @RequestBody @Valid PatchUpdateMusicDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.patchUpdate(id, dto));
    }
}
