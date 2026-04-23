package com.krev.musicloader.api.musicbrainz.dto.searchDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Recordings {
    private String id;
    private String title;
    @JsonProperty("artist-credit")
    private List<ArtistCredit> artistCredit;
    private List<Releases> releases;
}
