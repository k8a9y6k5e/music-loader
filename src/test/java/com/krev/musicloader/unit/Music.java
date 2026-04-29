package com.krev.musicloader.unit;

import com.krev.musicloader.api.service.MusicSearchService;
import com.krev.musicloader.api.service.dto.MusicSearchDto;
import com.krev.musicloader.exception.NotFoundException;
import com.krev.musicloader.music.MusicController;
import com.krev.musicloader.music.MusicEntity;
import com.krev.musicloader.music.MusicRepository;
import com.krev.musicloader.music.MusicService;
import com.krev.musicloader.music.dto.CreateMusicDto;
import com.krev.musicloader.music.dto.ListSearchedMusicDto;
import com.krev.musicloader.music.dto.PatchUpdateMusicDto;
import com.krev.musicloader.music.dto.PutUpdateMusicDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Music {
    @Mock
    private MusicRepository repository;

    @Mock
    private MusicSearchService clientService;

    @InjectMocks
    private MusicService service;

    @Test
    public void create() {
        when(clientService.search(anyString(), anyInt()))
                .thenReturn(new MusicSearchDto("test url", "test music", "test"));

        CreateMusicDto dto = new CreateMusicDto();

        dto.setName("test");
        dto.setTrack(1);

         service.create(dto);

        verify(repository).save(any(MusicEntity.class));
    }

    @Test
    public void createException() {
        when(clientService.search(anyString(), anyInt()))
                .thenThrow(NotFoundException.class);

        CreateMusicDto dto = new CreateMusicDto();

        dto.setName("test");
        dto.setTrack(1);

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void listMusicSearched() {
        when(clientService.list(anyString()))
                .thenReturn(new ArrayList<>());

        ListSearchedMusicDto dto = new ListSearchedMusicDto();

        dto.setName("test");

        List<MusicSearchDto> response = service.listMusicSearched(dto);

        assertThat(response).isEmpty();
        assertThat(response).isNotNull();
    }

    @Test
    public void listMusicSearchedException() {
        when(clientService.list(anyString()))
                .thenThrow(NotFoundException.class);

        ListSearchedMusicDto dto = new ListSearchedMusicDto();

        dto.setName("test");

        assertThatThrownBy(() -> service.listMusicSearched(dto))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void list() {
        List<MusicEntity> content = List.of(new MusicEntity());
        Page<MusicEntity> page = new PageImpl<>(content);

        when(repository.findAll(any(Pageable.class)))
                .thenReturn(page);

        Page<MusicEntity> response = service.list(Pageable.unpaged());

        assertThat(response).isNotEmpty();
        assertThat(response).isNotNull();
    }

    @Test
    public void listException() {
        when(repository.findAll(any(Pageable.class)))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> service.list(Pageable.unpaged()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void search() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(new MusicEntity()));

        MusicEntity response = service.search(1L);

        assertThat(response).isNotNull();
    }

    @Test
    public void searchException() {
        when(repository.findById(anyLong()))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> service.search(1L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void delete() {
        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    public void deleteException() {
        doThrow(NotFoundException.class)
                .when(repository).deleteById(anyLong());

        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void putUpdate() {
        PutUpdateMusicDto dto = new PutUpdateMusicDto();

        dto.setName("test");

        dto.setTrack(1);

        when(clientService.search(anyString(), anyInt()))
                .thenReturn(new MusicSearchDto("test.com", "tester", "test"));

        when(repository.save(any(MusicEntity.class)))
                .thenReturn(new MusicEntity());

        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(new MusicEntity()));

        MusicEntity response = service.putUpdate(1L, dto);

        assertThat(response).isNotNull();
    }

    @Test
    public void putUpdateRepositoryException() {
        PutUpdateMusicDto dto = new PutUpdateMusicDto();

        dto.setName("test");

        dto.setTrack(1);

        when(repository.findById(anyLong()))
                .thenThrow(NotFoundException.class);


        assertThatThrownBy(() -> service.putUpdate(1L, dto))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void putUpdateClientException() {
        PutUpdateMusicDto dto = new PutUpdateMusicDto();

        dto.setName("test");

        dto.setTrack(1);

        when(clientService.search(anyString(), anyInt()))
                .thenThrow(NotFoundException.class);

        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(new MusicEntity()));

        assertThatThrownBy(() -> service.putUpdate(1L, dto))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void PatchUpdate() {
        PatchUpdateMusicDto dto = new PatchUpdateMusicDto();

        dto.setName("test");

        dto.setTrack(1);

        dto.setResearch(true);

        when(clientService.search(anyString(), anyInt()))
                .thenReturn(new MusicSearchDto("test.com", "tester", "test"));

        when(repository.save(any(MusicEntity.class)))
                .thenReturn(new MusicEntity());

        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(new MusicEntity()));

        MusicEntity response = service.patchUpdate(1L, dto);

        assertThat(response).isNotNull();
    }

    @Test
    public void patchUpdateRepositoryException() {
        PatchUpdateMusicDto dto = new PatchUpdateMusicDto();

        dto.setName("test");

        dto.setTrack(1);

        dto.setResearch(true);

        when(repository.findById(anyLong()))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> service.patchUpdate(1L, dto))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void patchUpdateClientException() {
        PatchUpdateMusicDto dto = new PatchUpdateMusicDto();

        dto.setName("test");

        dto.setTrack(1);

        dto.setResearch(true);

        when(clientService.search(anyString(), anyInt()))
                .thenThrow(NotFoundException.class);

        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(new MusicEntity()));

        assertThatThrownBy(() -> service.patchUpdate(1L, dto))
                .isInstanceOf(NotFoundException.class);
    }
}
