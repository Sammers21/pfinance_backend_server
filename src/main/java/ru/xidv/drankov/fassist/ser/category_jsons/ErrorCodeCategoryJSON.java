package ru.xidv.drankov.fassist.ser.category_jsons;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorCodeCategoryJSON {

    @Getter
    private int error_code;

    @Getter
    private long category_id;

    public ErrorCodeCategoryJSON(long category_id) {
        this.error_code = 0;
        this.category_id = category_id;
    }
}
