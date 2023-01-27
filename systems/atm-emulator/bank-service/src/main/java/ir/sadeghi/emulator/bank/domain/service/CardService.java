package ir.sadeghi.emulator.bank.domain.service;
import org.springframework.stereotype.Service;

import ir.sadeghi.emulator.bank.domain.model.Card;
import ir.sadeghi.emulator.bank.domain.repository.CardRepository;

@Service
public class CardService {
    private CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card findCardByPan(String pin) {
        return cardRepository.findCardByPin(pin);
    }

    public String getPreferredAuthentication(String pin){
      return cardRepository.findCardByPin(pin).getPreferredAuthentication();
    }
}
