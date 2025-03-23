package ru.korett.biometriclab.user.role;

import jakarta.persistence.*;
import lombok.*;

/*
 * @author r.kashentsev
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_roles")
public class UserRole {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "role", unique = true, nullable = false)
    private Role role;

    public enum Role {
        ROLE_USER,
        ROLE_ADMIN
    }
}
