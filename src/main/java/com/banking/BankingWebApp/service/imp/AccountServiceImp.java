package com.banking.BankingWebApp.service.imp;

import com.banking.BankingWebApp.dto.AccountDto;
import com.banking.BankingWebApp.entity.Account;
import com.banking.BankingWebApp.mapper.AccountMapper;
import com.banking.BankingWebApp.repository.AccountRepository;
import com.banking.BankingWebApp.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImp implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImp(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto){
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return  AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
                .findAllById(Collections.singleton(id)) // Pass the id as a singleton collection
                .stream() // Convert Iterable<Account> to Stream<Account>
                .findFirst() // Get the first element of the stream
                .orElseThrow(() -> new RuntimeException("Account does not exist")); // Throw exception if not found

        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

            if(account.getBalance() < amount){
                throw new RuntimeException("Insufficient Fund!!");
            }

            //Logic
        double total = account.getBalance() - amount;
            account.setBalance(total);
            accountRepository.save(account);
            Account savedAccount = accountRepository.save(account);


        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
       List<Account> accounts = accountRepository.findAll();

       return accounts.stream().map((account ) -> AccountMapper.mapToAccountDto(account))
               .collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        accountRepository.deleteById(id);

    }


}
