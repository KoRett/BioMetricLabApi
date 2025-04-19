package ru.korett.biometriclab.parameter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author r.kashentsev
 */
@Service
@RequiredArgsConstructor
public class ParameterService {

    private final ParameterRepository parameterRepository;

    public List<Parameter> getAllParameters() {
        return parameterRepository.findAll();
    }

    public Parameter getParameterById(String id) {
        return parameterRepository.findById(id).orElse(null);
    }

    public Parameter saveParameter(Parameter parameter) {
        return parameterRepository.save(parameter);
    }

    public void deleteParameter(String id) {
        parameterRepository.deleteById(id);
    }
}
