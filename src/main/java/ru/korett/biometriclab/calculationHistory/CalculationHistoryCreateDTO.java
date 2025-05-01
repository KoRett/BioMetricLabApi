package ru.korett.biometriclab.calculationHistory;

import lombok.Data;

/*
 * @author r.kashentsev
 */
@Data
public class CalculationHistoryCreateDTO {
    private Float result;
    private Float variable;
    private String userId;
    private String parameterId;
}
