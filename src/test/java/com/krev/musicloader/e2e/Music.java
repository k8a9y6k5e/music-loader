package com.krev.musicloader.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class Music {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void create() throws Exception {
        String body = """
                {
                    "name": "flor de tangerina",
                    "track": 0
                }
                """;

        mockMvc.perform(post("/music")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Alceu Valença - Flor de Tangerina (Trilha Original de Velho Chico)"))
                .andExpect(jsonPath("$.url").value("https://www.youtube.com/watch?v=e4Ez99gCOiY"))
                .andExpect(jsonPath("$.artist").value("Deck"));
    }
}
