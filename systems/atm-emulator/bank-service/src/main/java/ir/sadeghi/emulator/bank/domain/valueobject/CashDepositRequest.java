package ir.sadeghi.emulator.bank.domain.valueobject;

import java.math.BigDecimal;

public class CashDepositRequest {
    private BigDecimal amount;
    private String pan;

    public CashDepositRequest(BigDecimal amount, String pan) {
        this.amount = amount;
        this.pan = pan;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}
