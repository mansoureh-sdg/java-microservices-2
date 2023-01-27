package ir.sadeghi.emulator.bank.domain.exception;

public class CashWithdrawFailureException extends Exception{
    public CashWithdrawFailureException() {
    }

    public CashWithdrawFailureException(String message) {
        super(message);
    }
}
