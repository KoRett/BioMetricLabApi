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
    public List<CalculationHistoryDTO> getHistory(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String parameterId
    ) {
        return historyService
                .getAllHistory()
                .stream()
                .map(this::mapCalculationHistory)
                .filter(dto -> userId == null || dto.getUserId().equals(userId))
                .filter(dto -> parameterId == null || dto.getParameterId().equals(parameterId))
                .toList();
    }

    @PostMapping
    public CalculationHistoryDTO createHistory(@RequestBody CalculationHistoryCreateDTO dto) {
        return mapCalculationHistory(historyService.createHistory(dto));
    }

    private CalculationHistoryDTO mapCalculationHistory(CalculationHistory calculationHistory) {
        return CalculationHistoryDTO.builder()
                .id(calculationHistory.getId())
                .variable(calculationHistory.getVariable())
                .result(calculationHistory.getResult())
                .date(calculationHistory.getDate())
                .userId(calculationHistory.getUser().getId())
                .parameterId(calculationHistory.getParameter().getId())
                .build();
    }
}
