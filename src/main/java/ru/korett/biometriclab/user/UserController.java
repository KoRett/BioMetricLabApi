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
    public UserDataResponse getData() {
        User user = userService.getCurrentUser();
        return UserDataResponse.builder()
                .UUID(user.getId())
                .email(user.getEmail())
                .role(UserDataResponse.Role
                        .mapFromDb(user.getUserRole().getRole())
                ).build();
    }

}
