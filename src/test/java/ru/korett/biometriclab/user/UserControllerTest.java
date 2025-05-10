package ru.korett.biometriclab.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.korett.biometriclab.user.role.UserRole;
import ru.korett.biometriclab.user.role.UserRoleService;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * @author r.kashentsev
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserRoleService userRoleService;

    private final String userId = UUID.randomUUID().toString();
    private final String email = "user1@mail.com";

    @BeforeEach
    void setUp() {
        User testUser = new User();
        testUser.setId(userId);
        testUser.setEmail(email);
        UserRole roleUser = new UserRole();
        roleUser.setRole(UserRole.Role.ROLE_USER);
        testUser.setUserRole(roleUser);

        when(userService.getCurrentUser()).thenReturn(testUser);

        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    @DisplayName("GET /users/me returns current user data")
    void testGetData() throws Exception {
        mockMvc.perform(get("/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(userId))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.role").value("USER"));
    }
}
