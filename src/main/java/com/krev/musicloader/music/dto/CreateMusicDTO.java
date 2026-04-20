package com.krev.musicloader.music.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreateMusicDTO {
    @NotBlank(message = "name of music can't be empty")
    private String name;
}
