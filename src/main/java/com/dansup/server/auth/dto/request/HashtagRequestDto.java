package com.dansup.server.auth.dto.request;

import lombok.Getter;

@Getter
public class HashtagRequestDto {

    private String hashtag;

    public void updateHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
