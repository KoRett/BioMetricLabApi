package ru.korett.biometriclab.calculationHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.korett.biometriclab.parameter.Parameter;
import ru.korett.biometriclab.user.User;

import java.time.LocalDateTime;
import java.util.List;

/*
 * @author r.kashentsev
 */
@Service
@RequiredArgsConstructor
public class CalculationHistoryService {

    private final CalculationHistoryRepository historyRepository;

    public List<CalculationHistory> getAllHistory() {
        return historyRepository.findAll();
    }

    public CalculationHistory getHistoryById(String id) {
        return historyRepository.findById(id).orElse(null);
    }

    public CalculationHistory createHistory(CalculationHistoryCreateDTO dto) {
        CalculationHistory history = CalculationHistory.builder()
                .temperature(dto.getTemperature())
                .result(dto.getResult())
                .date(LocalDateTime.now())
                .user(User.builder().id(dto.getUserId()).build())
                .parameter(Parameter.builder().id(dto.getParameterId()).build())
                .build();

        return historyRepository.save(history);
    }

    public void deleteHistory(String id) {
        historyRepository.deleteById(id);
    }
}
