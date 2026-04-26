package com.krev.musicloader.api.invidious;

import com.krev.musicloader.api.client.MusicApiClient;
import com.krev.musicloader.api.service.dto.MusicSearchDto;

import com.krev.musicloader.api.invidious.dto.SearchInvidiousDto;
import com.krev.musicloader.exception.NotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class InvidiousClient implements MusicApiClient {
    public MusicSearchDto searchMusic(String name) {
        HttpEntity<Void> request = new HttpEntity<>(HttpHeaders.EMPTY);

        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromUriString("https://inv.thepixora.com/api/v1/search")
                .queryParam("q", name)
                .queryParam("type", "video")
                .build()
                .encode()
                .toUri();

        ResponseEntity<SearchInvidiousDto[]> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                SearchInvidiousDto[].class
        );

        nullValidation(response.getBody());

        String url = getUrl(response.getBody()[0].getVideoId());

        MusicSearchDto musicSearch = new MusicSearchDto(url, getArtists(response.getBody(), 0), getName(response.getBody(), 0));

        return musicSearch;
    }

    private void nullValidation(SearchInvidiousDto[] searched) {
        if(searched.length == 0)
            throw new NotFoundException("Music not founded");
    }

    private String getUrl(String videoId) {
        return "https://www.youtube.com/watch?v="+videoId;
    }

    private String getArtists(SearchInvidiousDto[] searched, Integer track) {
        return searched[track].getAuthor();
    }

    private String getName(SearchInvidiousDto[] searched, Integer track){
        return searched[track].getTitle();
    }
}
