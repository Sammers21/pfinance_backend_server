/*
 * Copyright 2017 Pavel Drankov, Sergey Shershakov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.xidv.drankov.fassist.service.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.xidv.drankov.fassist.dm.dao.Account;
import ru.xidv.drankov.fassist.dm.dao.Currency;
import ru.xidv.drankov.fassist.dm.dao.User;
import ru.xidv.drankov.fassist.dm.dmr.AccountRepository;
import ru.xidv.drankov.fassist.ser.account_jsons.*;
import ru.xidv.drankov.fassist.ser.shared_jsons.TokenJSON;
import ru.xidv.drankov.fassist.service.auth.AuthService;
import ru.xidv.drankov.fassist.service.currency.CurrencyService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static ru.xidv.drankov.fassist.util.Response.error_code_0;
import static ru.xidv.drankov.fassist.util.Response.error_code_1;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final Logger log = LoggerFactory.getLogger(AccountController.class.getName());
    private final AuthService authService;
    private final CurrencyService currencyService;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @RequestMapping(value = "/open", method = RequestMethod.POST)
    ResponseEntity<?> open(
            @RequestBody OpenAccountRequestJSON JSON
    ) {

        log.info("account open call " + JSON);
        /////////////////Find user and currency/////////////////////////////////

        User user;
        Currency currency;
        try {

            user = authService.getUserByToken(JSON.getAuth_token());
            currency = currencyService.getCurrency(JSON.getCurrency());

        } catch (Exception e) {
            log.error(e.getMessage());
            return error_code_1();
        }

        /////////////////////////////////////////////////////////////////////////

        Account new_account = new Account(
                JSON.getName(),
                JSON.getType(),
                Instant.now().getEpochSecond(),
                user,
                currency
        );


        //save in DB
        accountRepository.save(new_account);

        return new ResponseEntity<>(new ErrorCodeAndAccountID(new_account.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/close", method = RequestMethod.POST)
    ResponseEntity<?> close(
            @RequestBody TokenAndAcIDRequestJSON JSON
    ) {
        log.info("account close call " + JSON);
        Account account;
        try {
            account = accountService.getAccountById(JSON.getAccount_id());
        } catch (Exception e) {

            log.error(e.getMessage());
            return error_code_1();
        }

        account.setClosedate(Instant.now().getEpochSecond());
        accountRepository.save(account);

        return error_code_0();
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    ResponseEntity<?> list(
            @RequestBody TokenJSON JSON
    ) {
        log.info("account list call " + JSON);
        User user;
        try {
            user = authService.getUserByToken(JSON.getAuth_token());
        } catch (Exception e) {
            log.error(e.getMessage());
            return error_code_1();
        }

        //fetch all users accounts and return
        List<AccountResponseJSON> account_list = user.getAccounts()
                .parallelStream()
                .map(Account::get_as_json)
                .collect(Collectors.toList());


        return new ResponseEntity<>(new AccountListResponseJSON(account_list), HttpStatus.OK);
    }


    @Autowired
    public AccountController(AuthService authService, CurrencyService currencyService, AccountRepository accountRepository, AccountService accountService) {
        this.authService = authService;
        this.currencyService = currencyService;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

}
