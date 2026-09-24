package service;

import Repository.jdbc.JdbcPaymentRepository;
import Repository.jdbc.JdbcReservationRepository;
import model.Payment;
import model.Reservation;
import model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentService {

    private JdbcPaymentRepository jdbcPayment;

    public PaymentService(){
        this.jdbcPayment = new JdbcPaymentRepository();
    }

    public Payment createPaiment(Reservation reservation , BigDecimal totalPrice){
        Payment payment = new Payment(reservation, totalPrice, PaymentStatus.PAID, LocalDate.now());
        this.jdbcPayment.save(payment);
        return  payment;
    }

}
