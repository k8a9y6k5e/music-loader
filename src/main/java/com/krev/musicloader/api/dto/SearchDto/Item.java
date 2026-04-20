package com.krev.musicloader.api.dto.SearchDto;

import lombok.Data;

import java.util.List;

@Data
public class Item {
    private ExternalUrls external_urls;
    private List<Artists> artists;
}
