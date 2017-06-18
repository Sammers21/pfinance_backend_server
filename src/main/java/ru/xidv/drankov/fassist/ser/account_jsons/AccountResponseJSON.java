package ru.xidv.drankov.fassist.ser.account_jsons;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountResponseJSON {

    @Getter
    private String name;

    @Getter
    private int type;

    @Getter
    private long account_id;

    @Getter
    private String currency;

    @Getter
    private double balance;

    @Getter
    private long opendate;

    @Getter
    private long closedate;

    public AccountResponseJSON(String name, int type, long account_id, String currency, double balance, long opendate, long closedate) {
        this.name = name;
        this.type = type;
        this.account_id = account_id;
        this.currency = currency;
        this.balance = balance;
        this.opendate = opendate;
        this.closedate = closedate;
    }
}
