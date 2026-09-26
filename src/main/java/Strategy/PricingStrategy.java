package Strategy;

import model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PricingStrategy {
    BigDecimal calculerTotalPrice( BigDecimal roomPrice, LocalDate chickin , LocalDate chickout);
}