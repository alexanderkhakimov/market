package com.example.market.service;

import com.example.market.model.Account;
import com.example.market.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountReporistory;

    public Account saveAccount(Account account) {
        return accountReporistory.save(account);
    }

}
