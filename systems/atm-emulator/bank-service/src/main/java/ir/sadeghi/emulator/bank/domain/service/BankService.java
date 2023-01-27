package ir.sadeghi.emulator.bank.domain.service;

import ir.sadeghi.emulator.bank.domain.model.Card;
import ir.sadeghi.emulator.bank.domain.model.PreferredAuthentication;
import ir.sadeghi.emulator.bank.domain.exception.*;
import ir.sadeghi.emulator.bank.domain.valueobject.*;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankService {

    private AccountService accountService;
    private CardService cardService;

    public BankService(AccountService accountService, CardService cardService) {
        this.accountService = accountService;
        this.cardService = cardService;
    }

    public CashWithdrawResult cashWithdraw(BigDecimal amount, String pan) throws CashWithdrawFailureException {
        Card card = cardService.findCardByPan(pan);
        if (card == null) {
            throw new CashWithdrawFailureException("card with pin=" + pan + "not found Exception");
        }

        if (amount.compareTo(BigDecimal.ZERO) == -1) {
            throw new CashWithdrawFailureException("amount must be greater than zero");
        }

        try {
            accountService.cashWithdraw(amount, card.getAccount());
        } catch (AccountBusinessException | AccountDBException e) {
            throw new CashWithdrawFailureException(e.getMessage());
        }
        return new CashWithdrawResult(card.getAccount().getBalance(), card.getPan());
    }

    public CashDepositResult cashDeposit(BigDecimal amount, String pin) throws CashWithdrawFailureException {
        Card card = cardService.findCardByPan(pin);

        if (card == null) {
            throw new CashWithdrawFailureException("card with pin=" + pin + "not found Exception");
        }

        if (amount.compareTo(BigDecimal.ZERO) == -1) {
            throw new CashWithdrawFailureException("amount must be greater than zero");
        }

        try {
            accountService.cashDeposit(amount, card.getAccount());
        } catch (AccountBusinessException | AccountDBException e) {
            throw new CashWithdrawFailureException(e.getMessage());
        }
        return new CashDepositResult(card.getAccount().getBalance(), card.getPan());

    }

    public BigDecimal checkBalance(String pan) throws CashWithdrawFailureException {
        Card card = cardService.findCardByPan(pan);
        if (card == null) {
            return null;
        }
        return card.getAccount().getBalance();
    }

    public boolean authenticateWithPin(String pan, String pin) throws CashWithdrawFailureException, UserAuthenticationFailureException {
        Card card = cardService.findCardByPan(pan);
        if (card == null) {
            return false;
        }
        //todo check the hash value of password
        if (PreferredAuthentication.PIN.toString().equals(card.getPreferredAuthentication()) && card.getPin().equals(pin)) {
            return true;
        }

        return false;
    }

    public boolean authenticateWithFingerPrint(String pan, String fingerPrint) throws CashWithdrawFailureException {
        Card card = cardService.findCardByPan(pan);
        if (card == null) {
            return false;
        }

        if (PreferredAuthentication.FINGER_PRINT.toString().equals(card.getPreferredAuthentication()) &&
                card.getAccount().getOwner().getFingerPrint().equals(fingerPrint)) {
            return true;
        }

        return false;
    }

    public CardDTO checkCardNumber(String pan) {
        Card card = cardService.findCardByPan(pan);
        CardMapper cardMapper = new CardMapper();
        CardDTO cardDTO = cardMapper.mapEntityToDTO(card);
        return cardDTO;
    }

}
