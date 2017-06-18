package ru.xidv.drankov.fassist.ser.shared_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ErrorCodeJSON {


    @Getter
    @Setter
    int error_code;

    public ErrorCodeJSON(int i) {
        error_code = i;
    }
}
