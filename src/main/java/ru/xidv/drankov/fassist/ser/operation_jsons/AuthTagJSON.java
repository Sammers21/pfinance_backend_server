package ru.xidv.drankov.fassist.ser.operation_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthTagJSON {

    @Getter
    private String auth_token;

    @Getter
    private String tag;

    public AuthTagJSON(String auth_token, String tag) {
        this.auth_token = auth_token;
        this.tag = tag;
    }
}
