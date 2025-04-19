package ru.korett.biometriclab.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.korett.biometriclab.user.role.UserRole;

/*
 * @author r.kashentsev
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDataResponse {
    @Schema(description = "Идентификатор пользователя")
    private String UUID;
    @Schema(description = "Почта", example = "user1@mail.com")
    private String email;
    @Schema(description = "Роль", example = "USER")
    private Role role;

    enum Role {
        USER, ADMIN;

        static public Role mapFromDb(UserRole.Role userRole) {
            return switch (userRole) {
                case ROLE_USER -> Role.USER;
                case ROLE_ADMIN -> Role.ADMIN;
            };
        }
    }
}
