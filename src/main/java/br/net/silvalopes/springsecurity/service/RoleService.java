package br.net.silvalopes.springsecurity.service;

import org.springframework.stereotype.Service;
import br.net.silvalopes.springsecurity.entities.Role;
import br.net.silvalopes.springsecurity.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role buscarRolePorNome(String name) {
        return roleRepository.findByName(name);
    }



}
