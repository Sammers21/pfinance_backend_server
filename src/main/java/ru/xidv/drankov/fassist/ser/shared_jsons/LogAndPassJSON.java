package ru.xidv.drankov.fassist.ser.shared_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class LogAndPassJSON {

    public LogAndPassJSON(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String password;

}
