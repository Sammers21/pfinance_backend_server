package ru.xidv.drankov.fassist.ser.account_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OpenAccountRequestJSON {

    @Getter
    private String auth_token;

    @Getter
    private String name;

    @Getter
    private int type;

    @Getter
    private String currency;

    public OpenAccountRequestJSON(String auth_token, String name, int type, String currency) {
        this.auth_token = auth_token;
        this.name = name;
        this.type = type;
        this.currency = currency;
    }
}
