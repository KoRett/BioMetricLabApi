package ru.korett.biometriclab.calculationHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.korett.biometriclab.parameter.Parameter;
import ru.korett.biometriclab.parameter.ParameterRepository;
import ru.korett.biometriclab.user.User;
import ru.korett.biometriclab.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

/*
 * @author r.kashentsev
 */
@Service
@RequiredArgsConstructor
public class CalculationHistoryService {

    private final CalculationHistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final ParameterRepository parameterRepository;

    public List<CalculationHistory> getAllHistory() {
        return historyRepository.findAll();
    }

    public CalculationHistory getHistoryById(String id) {
        return historyRepository.findById(id).orElse(null);
    }

    public CalculationHistory createHistory(CalculationHistoryCreateDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        Parameter parameter = parameterRepository.findById(dto.getParameterId()).orElseThrow();

        CalculationHistory history = CalculationHistory.builder()
                .result(dto.getResult())
                .variable(dto.getVariable())
                .date(LocalDateTime.now())
                .user(user)
                .parameter(parameter)
                .build();

        return historyRepository.save(history);
    }

    public void deleteHistory(String id) {
        historyRepository.deleteById(id);
    }
}
