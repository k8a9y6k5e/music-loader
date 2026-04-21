package com.krev.musicloader.music.dto;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

@Data
public class PatchUpdateMusicDto {
    private String name;

    private Boolean research = false;

    @AssertTrue(message = "At least one field must be provided")
    public boolean isAtLeastOneFieldPresent() {
        return name != null || Boolean.TRUE.equals(research);
    }
}
