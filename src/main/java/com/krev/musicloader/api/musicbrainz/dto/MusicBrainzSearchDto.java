package com.krev.musicloader.api.musicbrainz.dto;

import com.krev.musicloader.api.musicbrainz.dto.searchDto.Recordings;
import lombok.Data;

import java.util.List;

@Data
public class MusicBrainzSearchDto {
    private List<Recordings> recordings;
}
