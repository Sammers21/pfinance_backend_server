package ru.xidv.drankov.fassist.ser.account_jsons;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class AccountListResponseJSON {

    @Getter
    private int error_code;

    @Getter
    private List<AccountResponseJSON> accounts;

    public AccountListResponseJSON(List<AccountResponseJSON> accounts) {
        this.accounts = accounts;
    }
}
