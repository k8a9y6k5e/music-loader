package com.krev.musicloader.music.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreateMusicDto {
    @NotBlank(message = "name of music can't be empty")
    private String name;
}
