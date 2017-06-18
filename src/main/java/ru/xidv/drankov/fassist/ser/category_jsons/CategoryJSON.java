package ru.xidv.drankov.fassist.ser.category_jsons;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class CategoryJSON {
    @Getter
    private long category_id;

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private Integer type;

    @Getter
    private List<Long> child_list;

    @Getter
    private double sum;

    public CategoryJSON(long category_id, String name, String description, Integer type, List<Long> child_list,
                        double sum) {
        this.category_id = category_id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.child_list = child_list;
        this.sum = sum;
    }
}
