package ir.sadeghi.emulator.bank.domain.repository;

import org.springframework.data.repository.CrudRepository;

import ir.sadeghi.emulator.bank.domain.model.Card;

public interface CardRepository extends CrudRepository<Card,Long> {
    Card findCardByPin(String pin);
}
