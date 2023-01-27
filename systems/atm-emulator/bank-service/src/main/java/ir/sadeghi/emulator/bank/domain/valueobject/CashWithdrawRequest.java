package ir.sadeghi.emulator.bank.domain.valueobject;

import java.math.BigDecimal;

public class CashWithdrawRequest {
    private String pan;
    private BigDecimal amount;

    public CashWithdrawRequest(String pan, BigDecimal amount) {
        this.pan = pan;
        this.amount = amount;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
