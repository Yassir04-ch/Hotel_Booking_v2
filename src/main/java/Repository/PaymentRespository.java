package Repository;

import model.Payment;
import model.Reservation;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRespository {

    void save(Payment payment);

    public void update(Payment payment);

    Optional<Payment> getPaymentByResevationID(Reservation reservation);
}
