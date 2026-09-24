package dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class InvoiceDTO {

    private BigDecimal subtotalHT;
    private BigDecimal vat;
    private BigDecimal totalTTC;
    private LocalDate issuedAt;

    public InvoiceDTO(BigDecimal subtotalHT, BigDecimal vat, BigDecimal totalTTC, LocalDate issuedAt) {
        this.subtotalHT = subtotalHT;
        this.vat = vat;
        this.totalTTC = totalTTC;
        this.issuedAt = issuedAt;
    }

    public BigDecimal getSubtotalHT() {
        return subtotalHT;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public BigDecimal getTotalTTC() {
        return totalTTC;
    }

    public LocalDate getIssuedAt() {
        return issuedAt;
    }
}