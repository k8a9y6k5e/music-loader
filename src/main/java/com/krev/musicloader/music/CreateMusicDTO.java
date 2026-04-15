package com.krev.musicloader.music;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreateMusicDTO {
    @NotBlank(message = "artist name can't be empty")
    private String artist;

    @NotBlank(message = "name of music can't be empty")
    private String name;
}
