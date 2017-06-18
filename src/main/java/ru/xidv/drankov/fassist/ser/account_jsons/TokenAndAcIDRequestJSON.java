package ru.xidv.drankov.fassist.ser.account_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenAndAcIDRequestJSON {

    @Getter
    private String auth_token;

    @Getter
    private long account_id;

    @Getter
    private long timestamp_from;

    public TokenAndAcIDRequestJSON(String auth_token, long account_id, long timestamp_from) {
        this.auth_token = auth_token;
        this.account_id = account_id;
        this.timestamp_from = timestamp_from;
    }
}
