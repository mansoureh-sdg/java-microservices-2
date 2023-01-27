package ir.sadeghi.emulator.bank.domain.repository;

import org.springframework.data.repository.CrudRepository;

import ir.sadeghi.emulator.bank.domain.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
   Account findAccountById(Long accountId);
}
