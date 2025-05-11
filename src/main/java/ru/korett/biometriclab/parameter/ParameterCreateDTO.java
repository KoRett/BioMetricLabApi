package ru.korett.biometriclab.parameter;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * @author r.kashentsev
 */
@Data
@AllArgsConstructor
public class ParameterCreateDTO {
    private String name;
    private String description;
    private String formula;
    private Float accuracyPercentage;
}
