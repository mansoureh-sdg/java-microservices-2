package ir.sadeghi.emulator.bank.domain.service;

import ir.sadeghi.emulator.bank.domain.model.Account;
import ir.sadeghi.emulator.bank.domain.model.AccountTransaction;
import ir.sadeghi.emulator.bank.domain.model.TransactionStatus;
import ir.sadeghi.emulator.bank.domain.model.TransactionType;
import ir.sadeghi.emulator.bank.domain.repository.*;
import ir.sadeghi.emulator.bank.domain.exception.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public AccountService() {
    }


    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    public void cashWithdraw(BigDecimal amount, Account account) throws AccountBusinessException, AccountDBException {
        validateAmount(amount);
        validateAccount(account);
        try {
            if (account.getBalance().compareTo(amount) == -1) {
                throw new AccountBusinessException("Withdrawal amount exceeded account balance");
            }
            BigDecimal currentBalance = account.getBalance();
            BigDecimal newBalance = currentBalance.subtract(amount);
            account.setBalance(newBalance);
            AccountTransaction transaction = addAccountTransactionToAccount(LocalDateTime.now(), UUID.randomUUID().toString(), amount,
                    TransactionType.CASHWITDROW.toString(), TransactionStatus.COMPLETE.toString());
            addTransactionToAccountTransactions(account, transaction);
            accountRepository.save(account);
        } catch (Exception e) {
            throw new AccountDBException(e.getMessage());
        }
    }


    public void cashDeposit(BigDecimal amount, Account account) throws AccountBusinessException, AccountDBException {
        validateAmount(amount);
        validateAccount(account);
        try {
            BigDecimal currentBalance = account.getBalance();
            BigDecimal newBalance = currentBalance.add(amount);
            account.setBalance(newBalance);
            AccountTransaction transaction = addAccountTransactionToAccount(LocalDateTime.now(), UUID.randomUUID().toString(), amount,
                    TransactionType.CASHDEPOSUT.toString(), TransactionStatus.COMPLETE.toString());
            addTransactionToAccountTransactions(account, transaction);
            accountRepository.save(account);
        } catch (Exception e) {
            throw new AccountDBException(e.getMessage());
        }
    }

    private void addTransactionToAccountTransactions(Account account, AccountTransaction transaction) {
        //todo load accountTransactionsList
        if (account.getTransactions() != null && !account.getTransactions().isEmpty()) {
            account.getTransactions().add(transaction);
        } else {
            Set<AccountTransaction> transactions = new HashSet<>();
            transactions.add(transaction);
            account.setTransactions(transactions);
        }
    }


    private AccountTransaction addAccountTransactionToAccount(LocalDateTime date, String transactionNumber, BigDecimal amount, String type, String status) {
        AccountTransaction accountTransaction = new AccountTransaction(date, transactionNumber, amount, type, status);
        transactionRepository.save(accountTransaction);
        return accountTransaction;
    }

    private void validateAccount(Account account) throws AccountBusinessException {
        if (account == null) {
            throw new AccountBusinessException("account not found exception");
        }
    }

    private void validateAmount(BigDecimal amount) throws AccountBusinessException {
        if (amount.compareTo(BigDecimal.ZERO) == -1) {
            throw new AccountBusinessException("mount must be greater than zero");
        }
    }
}

