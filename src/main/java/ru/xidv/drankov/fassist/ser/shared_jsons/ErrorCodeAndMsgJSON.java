package ru.xidv.drankov.fassist.ser.shared_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorCodeAndMsgJSON {

    @Getter
    private int error_code;

    @Getter
    private String error_msg;

    public ErrorCodeAndMsgJSON(int error_code, String error_msg) {
        this.error_code = error_code;
        this.error_msg = error_msg;
    }
}
