package ru.xidv.drankov.fassist.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xidv.drankov.fassist.dm.dao.Account;
import ru.xidv.drankov.fassist.dm.dmr.AccountRepository;

@Service
public class AccountService{

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountById(long id) throws Exception {
        Account byId = accountRepository.findById(id);
        if (byId == null) {
            throw new Exception("Account with id " + id + " not found");
        }
        return byId;
    }
}
