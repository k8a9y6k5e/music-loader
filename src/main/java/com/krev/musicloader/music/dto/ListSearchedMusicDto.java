package com.krev.musicloader.music.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ListSearchedMusicDto {
    @NotBlank(message = "name of music can't be empty")
    private String name;
}
