package br.net.silvalopes.springsecurity.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.net.silvalopes.springsecurity.entities.Tweet;
import br.net.silvalopes.springsecurity.repositories.TweetRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;

    @Transactional
    public Tweet salvarTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    public Page<Tweet> buscarTodos(Pageable pageable) {
        return tweetRepository.findAll(pageable);
    }

    public Optional<Tweet> buscarPorId(Long id) {
        return tweetRepository.findById(id);
    }

    @Transactional
    public void apagarTweetPorId(Long id) {
        tweetRepository.deleteById(id);
    }

}
