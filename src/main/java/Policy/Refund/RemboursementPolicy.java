package Policy.Refund;

import Policy.RefundPolicy;
import model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RemboursementPolicy implements RefundPolicy {

    @Override
    public BigDecimal calculateRefund(Reservation reservation) {
        LocalDate date = LocalDate.now();
        LocalDate checkin = reservation.getCheckIn();
        long dayBeforChick = ChronoUnit.DAYS.between(date,checkin);

        BigDecimal total = reservation.getTotalPrice();

        if (dayBeforChick > 14) {
            return total;
        }

        if (dayBeforChick >= 7) {
            return total.multiply(new BigDecimal("0.70"));
        }

        if (dayBeforChick >= 2) {
            return total.multiply(new BigDecimal("0.50"));
        }
        return BigDecimal.ZERO;
    }
}
