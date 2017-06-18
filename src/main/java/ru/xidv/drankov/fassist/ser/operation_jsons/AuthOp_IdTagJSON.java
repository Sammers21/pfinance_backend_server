package ru.xidv.drankov.fassist.ser.operation_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthOp_IdTagJSON {

    @Getter
    private String auth_token;

    @Getter
    private long op_id;

    @Getter
    private String tag;

    public AuthOp_IdTagJSON(String auth_token, long op_id, String tag) {
        this.auth_token = auth_token;
        this.op_id = op_id;
        this.tag = tag;
    }
}
