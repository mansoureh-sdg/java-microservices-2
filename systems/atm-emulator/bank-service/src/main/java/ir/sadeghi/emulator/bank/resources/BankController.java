package ir.sadeghi.emulator.bank.resources;

import ir.sadeghi.emulator.bank.domain.exception.CashWithdrawFailureException;
import ir.sadeghi.emulator.bank.domain.service.BankService;
import ir.sadeghi.emulator.bank.domain.valueobject.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/bank")
public class BankController {
    protected Logger logger = Logger.getLogger(BankController.class.getName());

    private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PutMapping("/cashWithdraw")
    public ResponseEntity<CashWithdrawResult> cashWithDraw(@RequestBody CashWithdrawRequest cashWithdrawRequest) throws CashWithdrawFailureException {
        CashWithdrawResult result = null;
        try {
            result = bankService.cashWithdraw(cashWithdrawRequest.getAmount(), cashWithdrawRequest.getPan());
            return new ResponseEntity<CashWithdrawResult>(result, HttpStatus.OK);

        } catch (CashWithdrawFailureException e) {
            logger.log(Level.WARNING, "Exception raised cashWithdraw REST Call " + e);
            return new ResponseEntity<CashWithdrawResult>(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception raised cashWithdraw REST Call " + e);
            return new ResponseEntity<CashWithdrawResult>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @PutMapping("/cashDeposit")
    public ResponseEntity<CashDepositResult> cashDeposit(@RequestBody CashDepositRequest cashDepositRequest) throws CashWithdrawFailureException {
        CashDepositResult result;
        try {
            result = bankService.cashDeposit(cashDepositRequest.getAmount(), cashDepositRequest.getPan());
            return new ResponseEntity<CashDepositResult>(result, HttpStatus.OK);

        } catch (CashWithdrawFailureException e) {
            logger.log(Level.WARNING, "Exception raised cashDeposit REST Call " + e);
            return new ResponseEntity<CashDepositResult>(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception raised cashDeposit REST Call " + e);
            return new ResponseEntity<CashDepositResult>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/getBalance")
    ResponseEntity<BigDecimal> getBalance(@RequestBody String pan) throws CashWithdrawFailureException {
        try {
            BigDecimal balance = bankService.checkBalance(pan);
            if (balance != null) {
                return new ResponseEntity<BigDecimal>(balance, HttpStatus.OK);
            } else {
                return new ResponseEntity<BigDecimal>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception raised getBalance REST Call", e);
            return new ResponseEntity<BigDecimal>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/checkCardNumber/{pan}")
    ResponseEntity<CheckingCardResult> checkCardNumber(@PathVariable("restaurant-id")  String pan) throws CashWithdrawFailureException {
        try {
            CardDTO card = bankService.checkCardNumber(pan);
            if (card != null) {
                return new ResponseEntity<CheckingCardResult>(new CheckingCardResult(card.getPan(), card.getOwnerName(), card.getPreferredAuthentication()), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception raised checkCardNumber REST Call", e);
            return new ResponseEntity<CheckingCardResult>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/authenticateWithPin")
    public ResponseEntity<AuthenticationResult> authenticateWithPin(@RequestBody CardAuthenticationRequest cardAuthenticationRequest) throws CashWithdrawFailureException {
        boolean result = false;
        try {
            result = bankService.authenticateWithPin(cardAuthenticationRequest.getPan(), cardAuthenticationRequest.getPin());
            if (result) {
                return new ResponseEntity<AuthenticationResult>(new AuthenticationResult(cardAuthenticationRequest.getPan(), true), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pan or pin is not correct!");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception raised authenticateWithPin REST Call", e);
            return new ResponseEntity<AuthenticationResult>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/authenticateWithFingerPrint")
    public ResponseEntity<AuthenticationResult> authenticateWithFingerPrint(@RequestBody CardAuthenticationRequest cardAuthenticationRequest) throws CashWithdrawFailureException {
        boolean result = false;
        try {
            result = bankService.authenticateWithFingerPrint(cardAuthenticationRequest.getPan(), cardAuthenticationRequest.getFingerPrint());

            if (result) {
                return new ResponseEntity<AuthenticationResult>(new AuthenticationResult(cardAuthenticationRequest.getPan(), true), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pin or pan is not correct");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception raised authenticateWithFingerPrint REST Call", e);
            return new ResponseEntity<AuthenticationResult>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Fallback method for checkCardNumber()
     *
     * @param pan
     * @return
     */
    public ResponseEntity<CheckingCardResult> defaultCard(
            @PathVariable String pan) {
        return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
    }
}

