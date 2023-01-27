package ir.sadeghi.emulator.bank.domain.repository;

import org.springframework.data.repository.CrudRepository;

import ir.sadeghi.emulator.bank.domain.model.AccountTransaction;

public interface TransactionRepository extends CrudRepository<AccountTransaction,Long> {
}
