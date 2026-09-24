package Policy;

import model.Reservation;

import java.math.BigDecimal;

public interface RefundPolicy {
    BigDecimal calculateRefund(Reservation reservation);
}
