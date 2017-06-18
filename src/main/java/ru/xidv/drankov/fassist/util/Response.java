package ru.xidv.drankov.fassist.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.xidv.drankov.fassist.ser.shared_jsons.ErrorCodeJSON;

public class Response {

    public static ResponseEntity<ErrorCodeJSON> error_code_1() {
        return new ResponseEntity<>(new ErrorCodeJSON(1), HttpStatus.OK);
    }

    public static ResponseEntity<ErrorCodeJSON> error_code_0() {
        return new ResponseEntity<>(new ErrorCodeJSON(0), HttpStatus.OK);
    }
}
