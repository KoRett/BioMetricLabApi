package ru.korett.biometriclab.calculationHistory;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/*
 * @author r.kashentsev
 */
@Data
@Builder
public class CalculationHistoryDTO {
    private String id;
    private Float result;
    private Float variable;
    private LocalDateTime date;
    private String userId;
    private String parameterId;
}