package com.krev.musicloader.music.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreateMusicDto {
    @NotBlank(message = "name of music can't be empty")
    private String name;
    @NotNull(message = "track of the music can't be empty")
    private Integer track;
}
