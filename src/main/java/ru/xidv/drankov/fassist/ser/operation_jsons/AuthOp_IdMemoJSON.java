package ru.xidv.drankov.fassist.ser.operation_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthOp_IdMemoJSON {

    @Getter
    private String auth_token;

    @Getter
    private long op_id;

    @Getter
    private String memo;

    public AuthOp_IdMemoJSON(String auth_token, long op_id, String memo) {
        this.auth_token = auth_token;
        this.op_id = op_id;
        this.memo = memo;
    }
}
