package service;

import Repository.jdbc.JdbcInvoiceRepository;
import Repository.jdbc.JdbcPaymentRepository;
import exception.InvoiceNotFound;
import exception.PaymentNotFound;
import model.Invoice;
import model.Payment;
import model.Reservation;

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

    public Invoice update(Payment payment) throws InvoiceNotFound {
            Invoice invoice = this.getInvoice(payment);
            BigDecimal subtotalHT = payment.getAmount();
            BigDecimal vat = subtotalHT.multiply(tva);
            BigDecimal totalTTC = subtotalHT.add(vat);

            invoice.setPayment(payment);
            invoice.setSubtotalHT(subtotalHT);
            invoice.setVat(vat);
            invoice.setTotalTTC(totalTTC);
            invoice.setIssuedAt(LocalDate.now());

            this.jdbcInvoice.update(invoice);

            return invoice;
    }

    public Invoice getInvoice(Payment payment) throws InvoiceNotFound {
        Invoice invoice = this.jdbcInvoice.getInvoiceByPayment(payment).orElseThrow(()->
                new InvoiceNotFound("Invoice Not found"));
        return invoice;
    }
}