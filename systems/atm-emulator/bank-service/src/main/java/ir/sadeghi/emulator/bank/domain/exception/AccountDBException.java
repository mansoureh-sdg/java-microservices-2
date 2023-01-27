package ir.sadeghi.emulator.bank.domain.exception;

public class AccountDBException  extends Exception{
    public AccountDBException() {
    }

    public AccountDBException(String message) {
        super(message);
    }
}
