package ir.sadeghi.emulator.bank.domain.exception;

public class AccountBusinessException extends Exception {
    public AccountBusinessException() {
    }

    public AccountBusinessException(String message) {
        super(message);
    }
}
