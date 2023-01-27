package ir.sadeghi.emulator.bank.domain.exception;

public class UserAuthenticationFailureException extends Exception {
    public UserAuthenticationFailureException(String pan) {
        super(pan);
    }
}
