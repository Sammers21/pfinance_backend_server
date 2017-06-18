package ru.xidv.drankov.fassist.ser.category_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthNameDescTypeParentJSON {

    @Getter
    private String auth_token;

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private long parent_id;

    public AuthNameDescTypeParentJSON(String auth_token, String name, String description, long parent_id) {
        this.auth_token = auth_token;
        this.name = name;
        this.description = description;
        this.parent_id = parent_id;
    }
}
