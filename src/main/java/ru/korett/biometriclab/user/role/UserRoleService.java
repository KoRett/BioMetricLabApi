package ru.korett.biometriclab.user.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

/*
 * @author r.kashentsev
 */
@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository repository;

    public UserRole save(UserRole user) {
        return repository.save(user);
    }

    public UserRole createIfNotExist(UserRole userRole) {
        if (repository.existsByRole(userRole.getRole())) {
            return null;
        }

        return save(userRole);
    }

    public UserRole getByRole(UserRole.Role role) throws RoleNotFoundException {
        return repository.findByRole(role)
                .orElseThrow(() -> new RoleNotFoundException("Роль не найдена"));
    }
}
