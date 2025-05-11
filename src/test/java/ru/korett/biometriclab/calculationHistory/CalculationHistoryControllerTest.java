package ru.korett.biometriclab.calculationHistory;

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
import ru.korett.biometriclab.parameter.Parameter;
import ru.korett.biometriclab.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * @author r.kashentsev
 */
@ExtendWith(MockitoExtension.class)
class CalculationHistoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CalculationHistoryService historyService;

    @InjectMocks
    private CalculationHistoryController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String userId = UUID.randomUUID().toString();
    private final String parameterId = UUID.randomUUID().toString();

    private CalculationHistory history;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(userId);

        Parameter parameter = new Parameter();
        parameter.setId(parameterId);

        history = CalculationHistory.builder()
                .id(UUID.randomUUID().toString())
                .user(user)
                .parameter(parameter)
                .variable(12f)
                .result(40f)
                .date(LocalDateTime.of(2025, 5, 5, 0, 0))
                .build();

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    @DisplayName("GET /calculationHistory returns full history")
    void testGetFullHistory() throws Exception {
        when(historyService.getAllHistory()).thenReturn(List.of(history));

        mockMvc.perform(get("/calculationHistory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(history.getId()))
                .andExpect(jsonPath("$[0].userId").value(userId))
                .andExpect(jsonPath("$[0].parameterId").value(parameterId))
                .andExpect(jsonPath("$[0].variable").value(12f))
                .andExpect(jsonPath("$[0].result").value(40f));
    }

    @Test
    @DisplayName("GET /calculationHistory filtered by userId and parameterId")
    void testGetFilteredHistory() throws Exception {
        when(historyService.getAllHistory()).thenReturn(List.of(history));

        mockMvc.perform(get("/calculationHistory")
                        .param("userId", userId)
                        .param("parameterId", parameterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(userId))
                .andExpect(jsonPath("$[0].parameterId").value(parameterId));
    }

    @Test
    @DisplayName("POST /calculationHistory creates new history")
    void testCreateHistory() throws Exception {
        CalculationHistoryCreateDTO dto = new CalculationHistoryCreateDTO(
                30f,
                12f,
                userId,
                parameterId
        );

        when(historyService.createHistory(dto)).thenReturn(history);

        mockMvc.perform(post("/calculationHistory")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(history.getId()))
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.parameterId").value(parameterId))
                .andExpect(jsonPath("$.variable").value(12f))
                .andExpect(jsonPath("$.result").value(40f));
    }
}
