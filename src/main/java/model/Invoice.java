package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Invoice {
    private UUID id;
    private UUID reservationId;
    private String invoiceNumber;
    private BigDecimal amount;
    private LocalDate createdAt;

    public Invoice(UUID reservationId, String invoiceNumber, BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.reservationId = reservationId;
        this.invoiceNumber = invoiceNumber;
        this.amount = amount;
        this.createdAt = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}