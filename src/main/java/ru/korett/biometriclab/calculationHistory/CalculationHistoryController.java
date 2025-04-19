package ru.korett.biometriclab.calculationHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @author r.kashentsev
 */
@RestController
@RequestMapping("/calculationHistory")
@RequiredArgsConstructor
public class CalculationHistoryController {

    private final CalculationHistoryService historyService;

    @GetMapping
    public List<CalculationHistoryDTO> getAllHistory() {
        return historyService
                .getAllHistory()
                .stream()
                .map(this::mapCalculationHistory)
                .toList();
    }

    @GetMapping("/{id}")
    public CalculationHistoryDTO getHistoryById(@PathVariable String id) {
        return mapCalculationHistory(historyService.getHistoryById(id));
    }

    @PostMapping
    public CalculationHistoryDTO createHistory(@RequestBody CalculationHistoryCreateDTO dto) {
        return mapCalculationHistory(historyService.createHistory(dto));
    }

    private CalculationHistoryDTO mapCalculationHistory(CalculationHistory calculationHistory) {
        return CalculationHistoryDTO.builder()
                .id(calculationHistory.getId())
                .result(calculationHistory.getResult())
                .temperature(calculationHistory.getTemperature())
                .date(calculationHistory.getDate())
                .userId(calculationHistory.getUser().getId())
                .parameterId(calculationHistory.getParameter().getId())
                .build();
    }

    @DeleteMapping("/{id}")
    public void deleteHistory(@PathVariable String id) {
        historyService.deleteHistory(id);
    }
}
