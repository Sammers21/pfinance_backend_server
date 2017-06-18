package ru.xidv.drankov.fassist.ser.category_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthCategoryJSON {

    @Getter
    private String auth_token;

    @Getter
    private long category_id;

    public AuthCategoryJSON(String auth_token, long category_id) {
        this.auth_token = auth_token;
        this.category_id = category_id;
    }
}
