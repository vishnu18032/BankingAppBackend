package com.banking.BankingWebApp.repository;

import com.banking.BankingWebApp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
