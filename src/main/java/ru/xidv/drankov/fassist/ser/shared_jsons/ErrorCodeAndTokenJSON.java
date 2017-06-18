package ru.xidv.drankov.fassist.ser.shared_jsons;


import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.xidv.drankov.fassist.dm.dao.SecToken;

@NoArgsConstructor
public class ErrorCodeAndTokenJSON {

    @Getter
    private int error_code;

    @Getter
    private String token;

    public ErrorCodeAndTokenJSON(int error_code, SecToken token) {
        this.error_code = error_code;
        this.token = token.getToken();
    }

}
