package com.krev.musicloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MusicLoaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicLoaderApplication.class, args);
    }

}
