package br.net.silvalopes.springsecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.net.silvalopes.springsecurity.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

}
