package ru.xidv.drankov.fassist.ser.category_jsons;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ErrorCodeAndCat_ListJSON {

    @Getter
    private int error_code;

    @Getter
    private List<CategoryJSON> cat_list;

    public ErrorCodeAndCat_ListJSON(List<CategoryJSON> cat_list) {
        this.cat_list = cat_list;
        error_code = 0;
    }
}
