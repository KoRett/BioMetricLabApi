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
    public List<CalculationHistory> getAllHistory() {
        return historyService.getAllHistory();
    }

    @GetMapping("/{id}")
    public CalculationHistory getHistoryById(@PathVariable String id) {
        return historyService.getHistoryById(id);
    }

    @PostMapping
    public CalculationHistory createHistory(@RequestBody CalculationHistoryCreateDTO dto) {
        return historyService.createHistory(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteHistory(@PathVariable String id) {
        historyService.deleteHistory(id);
    }
}
