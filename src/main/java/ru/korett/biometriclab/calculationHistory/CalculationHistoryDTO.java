package ru.korett.biometriclab.calculationHistory;

import lombok.Data;

import java.time.LocalDateTime;

/*
 * @author r.kashentsev
 */
@Data
public class CalculationHistoryDTO {
    private Integer id;
    private Float temperature;
    private Float result;
    private LocalDateTime date;
    private Integer userId;
    private Integer parameterId;
}