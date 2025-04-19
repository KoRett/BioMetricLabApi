package ru.korett.biometriclab.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author r.kashentsev
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Данные пользователя")
public class UserController {
    private final UserService userService;

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
}
