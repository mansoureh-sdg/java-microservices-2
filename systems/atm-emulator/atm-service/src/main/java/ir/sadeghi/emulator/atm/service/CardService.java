package ir.sadeghi.emulator.atm.service;

import ir.sadeghi.emulator.atm.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.logging.Logger;

@Service
public class CardService {
    static Logger logger = Logger.getLogger(CardService.class.getName());

    //@Qualifier("userInfoRestTemplate")
    @Autowired
    private RestTemplate restTemplate;
    
    public CardVerification checkCard(String pan) {
        String url = "http://bank-service/v1/bank/" +pan;
        ResponseEntity<CheckingCardResponse> result = restTemplate.getForEntity(url, CheckingCardResponse.class);

        logger.info(pan+"verified");
        return new CardVerification(result.getBody().getOwnerName(), result.getBody().getPan());
    }

    public CashDepositResult cashDeposit(String pan, BigDecimal amount) {
        logger.info("cash deposit "+pan);
        return new CashDepositResult(amount, pan);
    }

    public CashWithdrawResult cashWithdraw(String pan, BigDecimal amount) {
        logger.info("cash deposit "+pan);
        return new CashWithdrawResult(amount, pan);
    }
}
