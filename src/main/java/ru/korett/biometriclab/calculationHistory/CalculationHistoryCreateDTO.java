package ru.korett.biometriclab.calculationHistory;

import lombok.Data;

/*
 * @author r.kashentsev
 */
@Data
public class CalculationHistoryCreateDTO {
    private Float temperature;
    private Float result;
    private String userId;
    private String parameterId;
}
