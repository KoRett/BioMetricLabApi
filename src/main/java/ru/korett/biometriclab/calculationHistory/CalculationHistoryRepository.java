package ru.korett.biometriclab.calculationHistory;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * @author r.kashentsev
 */
public interface CalculationHistoryRepository extends JpaRepository<CalculationHistory, String> {
}