package ru.xidv.drankov.fassist.ser.category_jsons;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthNameDescTypeJSON {

    @Getter
    private String auth_token;

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private Integer type;

    public AuthNameDescTypeJSON(String auth_token, String name, String description, Integer type) {
        this.auth_token = auth_token;
        this.name = name;
        this.description = description;
        this.type = type;
    }
}
