package br.net.silvalopes.springsecurity.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import br.net.silvalopes.springsecurity.entities.Role;
import br.net.silvalopes.springsecurity.entities.User;
import br.net.silvalopes.springsecurity.service.RoleService;
import br.net.silvalopes.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AdminUserConfig implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleService.buscarRolePorNome(Role.Values.ADMIN.name());

        var userAdmin = userService.buscarUsuarioPorUsername("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin já existe");
                },
                () -> {
                    var user = new User();
                    user.setUsername("admin");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.setRoles(Set.of(roleAdmin));
                    userService.salvarUsuario(user);
                });
    }

}
