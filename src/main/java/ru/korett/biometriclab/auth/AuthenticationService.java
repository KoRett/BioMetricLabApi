package ru.korett.biometriclab.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.korett.biometriclab.auth.jwt.JwtAuthenticationResponse;
import ru.korett.biometriclab.auth.jwt.JwtService;
import ru.korett.biometriclab.auth.signup.SignUpRequest;
import ru.korett.biometriclab.user.User;
import ru.korett.biometriclab.user.UserService;
import ru.korett.biometriclab.auth.signin.SignInRequest;
import ru.korett.biometriclab.user.role.UserRole;
import ru.korett.biometriclab.user.role.UserRoleService;

import javax.management.relation.RoleNotFoundException;

/*
 * @author r.kashentsev
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) throws RoleNotFoundException {

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(userRoleService.getByRole(UserRole.Role.ROLE_USER))
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
