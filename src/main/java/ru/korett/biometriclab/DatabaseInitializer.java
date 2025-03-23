package ru.korett.biometriclab;

/*
 * @author r.kashentsev
 */

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korett.biometriclab.user.role.UserRole;
import ru.korett.biometriclab.user.role.UserRoleService;

@Component
@AllArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRoleService userRoleService;

    @Override
    @Transactional
    public void run(String... args) {
        userRoleService.createIfNotExist(
                UserRole.builder()
                        .role(UserRole.Role.ROLE_USER)
                        .build()
        );
        userRoleService.createIfNotExist(
                UserRole.builder()
                        .role(UserRole.Role.ROLE_ADMIN)
                        .build()
        );
    }
}

