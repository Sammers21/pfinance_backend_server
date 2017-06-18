package ru.xidv.drankov.fassist.ser.operation_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorCodeAndOp_IdJSON {

    @Getter
    private int error_code;

    @Getter
    private long op_id;

    public ErrorCodeAndOp_IdJSON(long op_id) {
        this.error_code = 0;
        this.op_id = op_id;
    }
}
