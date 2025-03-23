package ru.korett.biometriclab.user.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * @author r.kashentsev
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    Optional<UserRole> findByRole(UserRole.Role role);
    boolean existsByRole(UserRole.Role role);
}
