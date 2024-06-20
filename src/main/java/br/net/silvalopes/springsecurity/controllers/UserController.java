package br.net.silvalopes.springsecurity.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.net.silvalopes.springsecurity.controllers.dto.UserCreateDto;
import br.net.silvalopes.springsecurity.entities.Role;
import br.net.silvalopes.springsecurity.entities.User;
import br.net.silvalopes.springsecurity.service.RoleService;
import br.net.silvalopes.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/users")
    public ResponseEntity<Void> newUser(@RequestBody UserCreateDto userCreateDto) {
        var roleBasic = roleService.buscarRolePorNome(Role.Values.BASIC.name());

        Optional<User> userNovo = userService.buscarUsuarioPorUsername(userCreateDto.username());

        if (userNovo.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            var user = new User();
            user.setUsername(userCreateDto.username());
            user.setPassword(passwordEncoder.encode(userCreateDto.password()));
            user.setRoles(Set.of(roleBasic));
            userService.salvarUsuario(user);

            return ResponseEntity.ok().build();
        }

    }

    @GetMapping("/users")
    @PreAuthorize(value = "hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.buscarTodosUsuarios();
        return ResponseEntity.ok(users);
    }

}
