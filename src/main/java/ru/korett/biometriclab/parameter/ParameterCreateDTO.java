package ru.korett.biometriclab.parameter;

import lombok.Data;

/*
 * @author r.kashentsev
 */
@Data
public class ParameterCreateDTO {
    private String name;
    private String description;
    private String formula;
    private Float accuracyPercentage;
}
