package Repository;

import model.Invoice;
import model.Payment;

import java.util.Optional;

public interface InvoiceRepository {
    void save(Invoice invoice);
    void update(Invoice invoice);
    Optional<Invoice> getInvoiceByPayment(Payment payment);
}
