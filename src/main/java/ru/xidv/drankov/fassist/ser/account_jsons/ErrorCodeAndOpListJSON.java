package ru.xidv.drankov.fassist.ser.account_jsons;


import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.xidv.drankov.fassist.ser.operation_jsons.OperationJSON_without_acc;

import java.util.List;

@NoArgsConstructor
public class ErrorCodeAndOpListJSON {

    @Getter
    private int error_code;

    @Getter
    private List<OperationJSON_without_acc> operations;

    public ErrorCodeAndOpListJSON(List<OperationJSON_without_acc> operations) {
        this.operations = operations;
        error_code = 0;
    }
}
