package com.krev.musicloader.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import lombok.Getter;

import java.util.Base64;

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

        ResponseEntity<SpotifyMap> response = restTemplate.postForEntity(
                "https://accounts.spotify.com/api/token",
                request,
                SpotifyMap.class
        );

        token = response.getBody().getAccess_token();
    }

    private void tokenExistence() {
        if(token == null) auth();
    }

    
}