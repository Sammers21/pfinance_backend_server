package ru.xidv.drankov.fassist.ser.operation_jsons;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ErrorCodeAndOperationsJSON {

    @Getter
    private int error_code;

    @Getter
    private List<OperationJSON_without_tag> operations;

    public ErrorCodeAndOperationsJSON(List<OperationJSON_without_tag> operations) {
        this.operations = operations;
        error_code = 0;
    }
}
