package ru.korett.biometriclab.parameter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * @author r.kashentsev
 */
@ExtendWith(MockitoExtension.class)
class ParameterControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private ParameterController parameterController;

    @Mock
    private ParameterService parameterService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(parameterController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    @DisplayName("GET /parameters returns list of parameters")
    void testGetAllParameters() throws Exception {
        Parameter p1 = new Parameter(
                "id1",
                "name1",
                "description1",
                "formula1",
                40.1f,
                List.of());
        Parameter p2 = new Parameter("id2",
                "name2",
                null,
                "formula2",
                20.12f,
                List.of());
        when(parameterService.getAllParameters()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/parameters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("id1"))
                .andExpect(jsonPath("$[0].name").value("name1"))
                .andExpect(jsonPath("$[0].description").value("description1"))
                .andExpect(jsonPath("$[0].formula").value("formula1"))
                .andExpect(jsonPath("$[0].accuracyPercentage").value(40.1))
                .andExpect(jsonPath("$[1].id").value("id2"))
                .andExpect(jsonPath("$[1].name").value("name2"))
                .andExpect(jsonPath("$[1].description").doesNotExist())
                .andExpect(jsonPath("$[1].formula").value("formula2"))
                .andExpect(jsonPath("$[1].accuracyPercentage").value(20.12));
    }

    @Test
    @DisplayName("GET /parameters/{id} returns parameter by id")
    void testGetParameterById() throws Exception {
        String id = UUID.randomUUID().toString();
        Parameter param = new Parameter(id,
                "name",
                "description",
                "formula",
                100.0f,
                List.of());
        when(parameterService.getParameterById(id)).thenReturn(param);

        mockMvc.perform(get("/parameters/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("name"));
    }

    @Test
    @DisplayName("POST /parameters creates new parameter")
    void testCreateParameter() throws Exception {
        ParameterCreateDTO dto = new ParameterCreateDTO(
                "name",
                "description",
                "formula",
                30f);
        Parameter saved = new Parameter(UUID.randomUUID().toString(),
                dto.getName(),
                dto.getDescription(),
                dto.getFormula(),
                dto.getAccuracyPercentage(),
                List.of());
        when(parameterService.saveParameter(any(Parameter.class))).thenReturn(saved);

        mockMvc.perform(post("/parameters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.description").value(dto.getDescription()));

        verify(parameterService).saveParameter(any(Parameter.class));
    }

    @Test
    @DisplayName("PUT /parameters/{id} updates existing parameter")
    void testUpdateParameter() throws Exception {
        String id = UUID.randomUUID().toString();
        Parameter existing = new Parameter(id,
                "oldName",
                "oldDesc",
                "oldFormula",
                50f,
                List.of());
        when(parameterService.getParameterById(id)).thenReturn(existing);

        ParameterCreateDTO dto = new ParameterCreateDTO(
                "newName",
                "newDesc",
                "newFormula",
                90f);
        Parameter updated = new Parameter(id,
                dto.getName(),
                dto.getDescription(),
                dto.getFormula(),
                dto.getAccuracyPercentage(),
                List.of());
        when(parameterService.saveParameter(existing)).thenReturn(updated);

        mockMvc.perform(put("/parameters/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("newName"))
                .andExpect(jsonPath("$.accuracyPercentage").value(90f));

        verify(parameterService).getParameterById(eq(id));
        verify(parameterService).saveParameter(existing);
    }

    @Test
    @DisplayName("DELETE /parameters/{id} deletes parameter")
    void testDeleteParameter() throws Exception {
        String id = UUID.randomUUID().toString();

        mockMvc.perform(delete("/parameters/{id}", id))
                .andExpect(status().isOk());

        verify(parameterService).deleteParameter(id);
    }
}
