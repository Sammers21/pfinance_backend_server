package ru.xidv.drankov.fassist.ser.shared_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenJSON {

    @Getter
    private String auth_token;

    public TokenJSON(String auth_token) {
        this.auth_token = auth_token;
    }
}
