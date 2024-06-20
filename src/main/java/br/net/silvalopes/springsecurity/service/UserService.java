package br.net.silvalopes.springsecurity.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.net.silvalopes.springsecurity.entities.User;
import br.net.silvalopes.springsecurity.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Optional<User> buscarUsuarioPorUsername(String username){
        return repository.findByUsername(username);
    }

    @Transactional
    public User salvarUsuario(User user) {
        return repository.save(user);
    }

    public List<User> buscarTodosUsuarios(){
        return repository.findAll();
    }

    public Optional<User> buscarUsuarioPorId(UUID id) {
        return repository.findById(id);
    }

}
