package ru.korett.biometriclab.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.korett.biometriclab.auth.jwt.JwtAuthenticationResponse;
import ru.korett.biometriclab.auth.signin.SignInRequest;
import ru.korett.biometriclab.auth.signup.SignUpRequest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * @author r.kashentsev
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOKEN = "token";

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    @DisplayName("POST /auth/sign-up")
    void testSignUpSuccess() throws Exception {
        SignUpRequest req = new SignUpRequest(
                "user1@mail.com",
                "12345678"
        );

        JwtAuthenticationResponse resp = new JwtAuthenticationResponse(TOKEN);

        when(authenticationService.signUp(req)).thenReturn(resp);

        mockMvc.perform(post("/auth/sign-up")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(TOKEN));
    }

    @Test
    @DisplayName("POST /auth/sign-in")
    void testSignInSuccess() throws Exception {
        SignInRequest req = new SignInRequest(
                "user1@mail.com",
                "12345678"
        );

        JwtAuthenticationResponse resp = new JwtAuthenticationResponse(TOKEN);

        when(authenticationService.signIn(req)).thenReturn(resp);

        mockMvc.perform(post("/auth/sign-in")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(TOKEN));
    }
}
