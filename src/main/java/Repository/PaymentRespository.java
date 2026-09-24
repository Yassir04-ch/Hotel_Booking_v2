package Repository;

import model.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRespository {

    void save(Payment payment);

    Optional<Payment> getPaymentById(UUID id);
}
