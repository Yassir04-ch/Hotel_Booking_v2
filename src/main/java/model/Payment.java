package model;

import model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Payment {

    private UUID id;
    private UUID reservationId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDate paidAt;

    public Payment(UUID reservationId, BigDecimal amount ,PaymentStatus status , LocalDate paidAt) {
        this.id = UUID.randomUUID();
        this.reservationId = reservationId;
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

    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

}