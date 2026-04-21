package com.krev.musicloader.music.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PutUpdateMusicDto {
    @NotBlank
    private String name;
}
