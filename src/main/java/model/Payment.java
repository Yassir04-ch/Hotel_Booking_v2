package model;

import model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Payment {

    private UUID id;
    private Reservation reservation;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDate paidAt;

    public Payment(Reservation reservation, BigDecimal amount ,PaymentStatus status , LocalDate paidAt) {
        this.reservation = reservation;
        this.amount = amount;
        this.status = status;
        this.paidAt = paidAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDate paidAt) {
        this.paidAt = paidAt;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Reservation getReservationId() {
        return reservation;
    }

    public void setReservationId(Reservation reservation) {
        this.reservation = reservation;
    }

}