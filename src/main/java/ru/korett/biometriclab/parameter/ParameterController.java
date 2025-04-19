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
    public List<Parameter> getAllParameters() {
        return parameterService.getAllParameters();
    }

    @GetMapping("/{id}")
    public Parameter getParameterById(@PathVariable String id) {
        return parameterService.getParameterById(id);
    }

    @PostMapping
    public Parameter createParameter(@RequestBody ParameterCreateDTO dto) {
        Parameter parameter = Parameter.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .formula(dto.getFormula())
                .accuracyPercentage(dto.getAccuracyPercentage())
                .build();
        return parameterService.saveParameter(parameter);
    }

    @PutMapping("/{id}")
    public Parameter updateParameter(@PathVariable String id, @RequestBody Parameter parameter) {
        parameter.setId(id);
        return parameterService.saveParameter(parameter);
    }

    @DeleteMapping("/{id}")
    public void deleteParameter(@PathVariable String id) {
        parameterService.deleteParameter(id);
    }
}
