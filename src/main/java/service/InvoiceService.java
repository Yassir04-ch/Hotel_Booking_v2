package service;

import Repository.jdbc.JdbcInvoiceRepository;
import Repository.jdbc.JdbcPaymentRepository;
import model.Invoice;
import model.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceService {

    private static final BigDecimal tva = new BigDecimal("0.20");
    private JdbcInvoiceRepository jdbcInvoice;


    public InvoiceService(){
        this.jdbcInvoice = new JdbcInvoiceRepository();
    }

    public Invoice createInvoice(Payment payment) {
        BigDecimal subtotalHT = payment.getAmount();
        BigDecimal vat = subtotalHT.multiply(tva);
        BigDecimal totalTTC = subtotalHT.add(vat);
        Invoice invoice = new Invoice(payment, subtotalHT, vat, totalTTC, LocalDate.now());
        this.jdbcInvoice.save(invoice);
        return invoice;
    }
}