package br.net.silvalopes.springsecurity.controllers.dto;

public record LoginResponseDto(String accessToken, Long expiresIn) {

}
