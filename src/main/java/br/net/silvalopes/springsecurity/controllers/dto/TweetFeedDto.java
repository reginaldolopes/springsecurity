package br.net.silvalopes.springsecurity.controllers.dto;

import java.util.List;

public record TweetFeedDto(List<TweetFeedItemDto> tweetFeedItens, int page,
        int pageSize,
        int totalPages,
        long totalElements) {

}
