package com.krev.musicloader.api.invidious.dto;

import lombok.Data;
import java.util.List;

@Data
public class SearchInvidiousDto {
    private List<SearchDto> search;
}
