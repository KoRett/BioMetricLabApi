package ru.korett.biometriclab.user;

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
public class UserDTO {
    private String UUID;
    private String email;
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
