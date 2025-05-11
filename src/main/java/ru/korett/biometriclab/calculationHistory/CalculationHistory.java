package ru.korett.biometriclab.calculationHistory;

import jakarta.persistence.*;
import ru.korett.biometriclab.parameter.Parameter;
import lombok.*;
import ru.korett.biometriclab.user.User;

import java.time.LocalDateTime;

/*
 * @author r.kashentsev
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "calculation_history")
public class CalculationHistory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "result")
    private Float result;

    @Column(name = "variable")
    private Float variable;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "parameter_id")
    private Parameter parameter;
}
