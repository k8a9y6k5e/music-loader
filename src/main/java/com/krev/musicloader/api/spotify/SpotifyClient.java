package com.krev.musicloader.api.spotify;

import com.krev.musicloader.api.client.MusicApiClient;
import com.krev.musicloader.api.service.dto.MusicSearchDto;
import com.krev.musicloader.api.spotify.dto.SearchDto.Artists;
import com.krev.musicloader.api.spotify.dto.SpotifyAuthDto;
import com.krev.musicloader.api.spotify.dto.SpotifySearchDto;
import com.krev.musicloader.exception.NotFoundException;
import org.hibernate.validator.internal.constraintvalidators.bv.NullValidator;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.*;

@Component
public class SpotifyClient implements MusicApiClient {
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

    public MusicSearchDto searchMusic(String name, Integer track) {
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

        MusicSearchDto musicSearch = new MusicSearchDto(getUrl(response.getBody(),track), getArtists(response.getBody(),track), getName(response.getBody(), track));

        return musicSearch;
    }

    public List<MusicSearchDto> listMusic(String name) {
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

        List<MusicSearchDto> musicList = new ArrayList<>();

        for(Integer index = 0;  index < response.getBody().getTracks().getItems().size(); index++) {
            musicList.add(new MusicSearchDto(getUrl(response.getBody(), index), getArtists(response.getBody(), index), getName(response.getBody(), index)));
        }

        return musicList;
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

    private String getName(SpotifySearchDto musicSearched, Integer track) {
        return musicSearched.getTracks().getItems().get(0).getName();
    }
}