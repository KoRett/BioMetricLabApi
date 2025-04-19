package ru.korett.biometriclab.parameter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author r.kashentsev
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDTO {
    private String id;
    private String name;
    private String description;
    private String formula;
    private Integer accuracyPercentage;
}
