package ru.korett.biometriclab.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.korett.biometriclab.user.role.UserRole;
import ru.korett.biometriclab.user.role.UserRoleService;

import javax.management.relation.RoleNotFoundException;

/*
 * @author r.kashentsev
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Данные пользователя")
public class UserController {
    private final UserService userService;
    private final UserRoleService userRoleService;

    @Operation(summary = "Получение данных авторизованного пользователя")
    @GetMapping("/me")
    public UserDTO getData() {
        User user = userService.getCurrentUser();
        return UserDTO.builder()
                .UUID(user.getId())
                .email(user.getEmail())
                .role(UserDTO.Role.mapFromDb(user.getUserRole().getRole()))
                .build();
    }

    @PostMapping("/setRole")
    public UserDTO setRole(@RequestParam("isAdmin") Boolean isAdmin) throws RoleNotFoundException {
        User user = userService.getCurrentUser();
        if (isAdmin) {
            user.setUserRole(userRoleService.getByRole(UserRole.Role.ROLE_ADMIN));
        } else {
            user.setUserRole(userRoleService.getByRole(UserRole.Role.ROLE_USER));
        }
        userService.save(user);
        return UserDTO.builder()
                .UUID(user.getId())
                .email(user.getEmail())
                .role(UserDTO.Role.mapFromDb(user.getUserRole().getRole()))
                .build();
    }
}
