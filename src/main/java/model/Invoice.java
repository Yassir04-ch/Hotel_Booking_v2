package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Invoice {

    private UUID id;
    private Payment payment;
    private BigDecimal subtotalHT;
    private BigDecimal vat;
    private BigDecimal totalTTC;
    private LocalDateTime issuedAt;

    public Invoice(Payment payment, BigDecimal subtotalHT, BigDecimal vat, BigDecimal totalTTC, LocalDateTime issuedAt) {

        this.id = UUID.randomUUID();
        this.payment = payment;
        this.subtotalHT = subtotalHT;
        this.vat = vat;
        this.totalTTC = totalTTC;
        this.issuedAt = issuedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Payment getReservationId() {
        return payment;
    }

    public void setReservationId(Payment payment) {
        this.payment = payment;
    }

    public BigDecimal getSubtotalHT() {
        return subtotalHT;
    }

    public void setSubtotalHT(BigDecimal subtotalHT) {
        this.subtotalHT = subtotalHT;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getTotalTTC() {
        return totalTTC;
    }

    public void setTotalTTC(BigDecimal totalTTC) {
        this.totalTTC = totalTTC;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }
}