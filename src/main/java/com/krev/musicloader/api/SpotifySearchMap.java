package com.krev.musicloader.api;

import lombok.Data;
import java.util.List;

@Data
public class SpotifySearchMap {
    private Tracks tracks;
}

@Data
class Tracks {
    private List<Item> items;
}

@Data
class Item {
    private ExternalUrls external_urls;
    private List<Artists> artists;
}

@Data
class Artists {
    private String names;
}

@Data
class ExternalUrls {
    private String spotify;
}