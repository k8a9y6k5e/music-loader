package com.krev.musicloader.api.invidious;

import com.krev.musicloader.api.orchestrator.MusicSearchDto;

import com.krev.musicloader.api.invidious.dto.SearchInvidiousDto;
import com.krev.musicloader.exception.NotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class InvidiousClient {
    public MusicSearchDto searchMusic(String name) {
        HttpEntity<Void> request = new HttpEntity<>(HttpHeaders.EMPTY);

        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromUriString("https://invidious.io.lol/api/v1/search")
                .queryParam("q", name)
                .build()
                .encode()
                .toUri();

        ResponseEntity<SearchInvidiousDto> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                SearchInvidiousDto.class
        );

        nullValidation(response.getBody());

        String url = getUrl(response.getBody().getSearch().get(0).getVideoId());

        MusicSearchDto musicSearch = new MusicSearchDto(url, response.getBody().getSearch().get(0).getAuthor());

        return musicSearch;
    }

    private String getUrl(String videoId) {
        return "https://www.youtube.com/watch?v="+videoId;
    }

    private void nullValidation(SearchInvidiousDto responseBody) {
        if(responseBody.getSearch().toArray().length == 0){
            throw new NotFoundException("Music not founded");
        }
    }
}
