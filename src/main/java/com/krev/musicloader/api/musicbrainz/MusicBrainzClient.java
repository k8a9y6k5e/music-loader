package com.krev.musicloader.api.musicbrainz;

import com.krev.musicloader.api.musicbrainz.dto.MusicBrainzSearchDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class MusicBrainzClient {
    @Value("${musicBrainz.client.contact}")
    private String contact;

    private String url = "https://musicbrainz.org/ws/2/recording/";

    private ResponseEntity<MusicBrainzSearchDto> search(String name){
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("User-Agent", "MusicLoader/1.0 "+contact);

        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("query", name)
                .queryParam("fmt", "json")
                .build()
                .encode()
                .toUri();

        ResponseEntity<MusicBrainzSearchDto> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                MusicBrainzSearchDto.class
        );

        return response;
    }
}
