package com.krev.musicloader.api.spotify;

import com.krev.musicloader.api.orchestrator.MusicSearchDto;
import com.krev.musicloader.api.spotify.dto.SearchDto.Artists;
import com.krev.musicloader.api.spotify.dto.SpotifyAuthDto;
import com.krev.musicloader.api.spotify.dto.SpotifySearchDto;
import com.krev.musicloader.exception.NotFoundException;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class SpotifyClient {
    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private String credentials;

    private void createCredentials() {
        String auth = clientId + ":" + clientSecret;
        credentials = Base64.getEncoder().encodeToString(auth.getBytes());

    }

    @Getter
    private String token;

    private void auth() {
        createCredentials();

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Basic " + credentials);

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<SpotifyAuthDto> response = restTemplate.postForEntity(
                "https://accounts.spotify.com/api/token",
                request,
                SpotifyAuthDto.class
        );

        token = response.getBody().getAccess_token();
    }

    private void ensureToken() {
        if(token == null) auth();
    }

    public MusicSearchDto searchMusic(String name) {
        ensureToken();

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Authorization", "Bearer " + token);

        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromUriString("https://api.spotify.com/v1/search")
                .queryParam("q", "track:"+name)
                .queryParam("type", "track")
                .build()
                .encode()
                .toUri();

        ResponseEntity<SpotifySearchDto> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                SpotifySearchDto.class
                );

        nullVerification(response);

        MusicSearchDto musicSearch = new MusicSearchDto(getUrl(response.getBody(),0), getArtists(response.getBody(),0));

        return musicSearch;
    }

    private void nullVerification(ResponseEntity<SpotifySearchDto> response) {
        if(response.getBody().getTracks().getItems().toArray().length == 0){
            throw new NotFoundException("Music not founded");
        }
    };

    private String getUrl(SpotifySearchDto musicSearched, Integer track) {
        return musicSearched.getTracks().getItems().get(track).getExternal_urls().getSpotify();
    }

    private String getArtists(SpotifySearchDto musicSearched, Integer track) {
        List<String> artistsNames = new ArrayList<>();

        for (Artists artists : musicSearched.getTracks().getItems().get(track).getArtists()) {
            artistsNames.add(artists.getName());
        }

        return String.join(", ", artistsNames);
    }
}