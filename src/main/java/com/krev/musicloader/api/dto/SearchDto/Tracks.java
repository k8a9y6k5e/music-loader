package com.krev.musicloader.api.dto.SearchDto;

import lombok.Data;

import java.util.List;

@Data
public class Tracks {
    private List<Item> items;
}
