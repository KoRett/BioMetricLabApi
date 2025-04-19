package ru.korett.biometriclab.parameter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @author r.kashentsev
 */
@RestController
@RequestMapping("/parameters")
@RequiredArgsConstructor
public class ParameterController {

    private final ParameterService parameterService;

    @GetMapping
    public List<ParameterDTO> getAllParameters() {
        return parameterService
                .getAllParameters()
                .stream()
                .map(this::mapParameter)
                .toList();
    }

    @GetMapping("/{id}")
    public ParameterDTO getParameterById(@PathVariable String id) {
        return mapParameter(parameterService.getParameterById(id));
    }

    @PostMapping
    public ParameterDTO createParameter(@RequestBody ParameterCreateDTO dto) {
        Parameter parameter = Parameter.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .formula(dto.getFormula())
                .accuracyPercentage(dto.getAccuracyPercentage())
                .build();
        return mapParameter(parameterService.saveParameter(parameter));
    }

    @PutMapping("/{id}")
    public ParameterDTO updateParameter(@PathVariable String id, @RequestBody ParameterCreateDTO dto) {
        Parameter parameter = parameterService.getParameterById(id);
        parameter.setName(dto.getName());
        parameter.setFormula(dto.getFormula());
        parameter.setDescription(dto.getDescription());
        parameter.setAccuracyPercentage(dto.getAccuracyPercentage());
        return mapParameter(parameterService.saveParameter(parameter));
    }

    private ParameterDTO mapParameter(Parameter parameter) {
        return ParameterDTO.builder()
                .id(parameter.getId())
                .name(parameter.getName())
                .formula(parameter.getFormula())
                .accuracyPercentage(parameter.getAccuracyPercentage())
                .description(parameter.getDescription())
                .build();
    }

    @DeleteMapping("/{id}")
    public void deleteParameter(@PathVariable String id) {
        parameterService.deleteParameter(id);
    }
}
