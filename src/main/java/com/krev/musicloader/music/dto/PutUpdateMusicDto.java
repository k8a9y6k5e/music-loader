package com.krev.musicloader.music.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PutUpdateMusicDto {
    @NotBlank(message = "name of music can't be empty")
    private String name;
    @NotBlank(message = "track of the music can't be empty")
    private Integer track;
}
