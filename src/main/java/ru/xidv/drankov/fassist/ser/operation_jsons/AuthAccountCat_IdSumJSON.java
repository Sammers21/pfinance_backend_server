package ru.xidv.drankov.fassist.ser.operation_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class AuthAccountCat_IdSumJSON {

    @Getter
    private String auth_token;

    @Getter
    private long account_id;

    @Getter
    private long category_id;

    @Getter
    private double sum;

    public AuthAccountCat_IdSumJSON(String auth_token, long account_id, long category_id, double sum) {
        this.auth_token = auth_token;
        this.account_id = account_id;
        this.category_id = category_id;
        this.sum = sum;
    }
}
