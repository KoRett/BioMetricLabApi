package ru.korett.biometriclab.parameter;

import jakarta.persistence.*;
import lombok.*;
import ru.korett.biometriclab.calculationHistory.CalculationHistory;

import java.util.List;

/*
 * @author r.kashentsev
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parameters")
public class Parameter {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", length = 100, unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "formula", length = 100)
    private String formula;

    @Column(name = "accuracy_percentage")
    private Float accuracyPercentage;

    @OneToMany(mappedBy = "parameter", cascade = CascadeType.ALL)
    private List<CalculationHistory> calculationHistories;
}
