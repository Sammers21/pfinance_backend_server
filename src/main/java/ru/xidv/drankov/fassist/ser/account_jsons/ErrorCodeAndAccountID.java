package ru.xidv.drankov.fassist.ser.account_jsons;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorCodeAndAccountID {

    @Getter
    private int error_code;

    @Getter
    private long account_id;

    public ErrorCodeAndAccountID(long account_id) {
        this.error_code = 0;
        this.account_id = account_id;
    }
}
