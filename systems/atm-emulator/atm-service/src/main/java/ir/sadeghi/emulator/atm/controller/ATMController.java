package ir.sadeghi.emulator.atm.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ir.sadeghi.emulator.atm.dto.*;
import org.springframework.cloud.client.discovery.DiscoveryClient;


import ir.sadeghi.emulator.atm.service.CardService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin()
@Configuration
public class ATMController {
    @Autowired
    private CardService cardService;

    @Autowired
    DiscoveryClient client;

    @RequestMapping("/service")
    public List<String> home() {
        return client.getServices();
    }

    @RequestMapping({"/checkCard"})
    @HystrixCommand(fallbackMethod = "defaultCheckCard")
    public ResponseEntity<CardVerification> checkCard(@RequestBody String pan) {
        CardVerification cardVerification = cardService.checkCard(pan);
        return new ResponseEntity<>(cardVerification, HttpStatus.OK);
    }

    @RequestMapping({"/cashDeposit"})
    @HystrixCommand(fallbackMethod = "defaultCashDeposit")
    public ResponseEntity<CashDepositResult> cashDeposit(@RequestBody String pan, BigDecimal amount) {
        CashDepositResult cashDepositResult = cardService.cashDeposit(pan, amount);
        return new ResponseEntity<>(cashDepositResult, HttpStatus.OK);
    }

    @RequestMapping({"/cashWithdraw"})
    @HystrixCommand(fallbackMethod = "defaultCashWithdraw")
    public ResponseEntity<CashWithdrawResult> cashWithdraw(@RequestBody String pan, BigDecimal amount) {
        CashWithdrawResult cashWithdrawResult = cardService.cashWithdraw(pan, amount);
        return new ResponseEntity<>(cashWithdrawResult, HttpStatus.OK);
    }


    /**
     * Fallback method for checkCard
     * @param pan
     * @return
     */
    public ResponseEntity<CardVerification> defaultCheckCard(
            @RequestBody String pan) {
        return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
    }

    /**
     * Fallback method for cashDeposit
     * @param pan
     * @param amount
     * @return
     */
    public ResponseEntity<CashDepositResult> defaultCashDeposit(@RequestBody String pan, BigDecimal amount) {
        return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
    }

    /**
     *  Fallback method for cashWithdraw
     * @param pan
     * @param amount
     * @return
     */
    public ResponseEntity<CashWithdrawResult> defaultCashWithdraw(@RequestBody String pan, BigDecimal amount) {
        CashWithdrawResult cashWithdrawResult = cardService.cashWithdraw(pan, amount);
        return new ResponseEntity<>(cashWithdrawResult, HttpStatus.OK);
    }

}
