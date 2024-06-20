package br.net.silvalopes.springsecurity.controllers;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.net.silvalopes.springsecurity.controllers.dto.TweetCreateDto;
import br.net.silvalopes.springsecurity.controllers.dto.TweetFeedDto;
import br.net.silvalopes.springsecurity.controllers.dto.TweetFeedItemDto;
import br.net.silvalopes.springsecurity.entities.Role;
import br.net.silvalopes.springsecurity.entities.Tweet;
import br.net.silvalopes.springsecurity.service.TweetService;
import br.net.silvalopes.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TweetController {

    private final TweetService tweetService;
    private final UserService userService;

    @GetMapping("/feed")
    public ResponseEntity<TweetFeedDto> feed(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        var tweets = tweetService.buscarTodos(
                PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
                .map(tweet -> new TweetFeedItemDto(
                        tweet.getTweetId(),
                        tweet.getContent(),
                        tweet.getUser().getUsername()));

        return ResponseEntity.ok(new TweetFeedDto(
                tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements()));
    }

    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(@RequestBody TweetCreateDto dto, JwtAuthenticationToken token) {

        var user = userService.buscarUsuarioPorId(UUID.fromString(token.getName()));

        Tweet tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(dto.content());

        tweetService.salvarTweet(tweet);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long tweetId, JwtAuthenticationToken token) {

        var user = userService.buscarUsuarioPorId(UUID.fromString(token.getName()));

        var tweet = tweetService.buscarPorId(tweetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var isAdmin = user.get().getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if (isAdmin || tweet.getUser().getUserId().equals(UUID.fromString(token.getName()))) {
            tweetService.apagarTweetPorId(tweetId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

}
