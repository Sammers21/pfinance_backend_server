/*
 * Copyright 2017 Pavel Drankov, Sergey Shershakov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
