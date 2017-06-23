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
package ru.xidv.drankov.fassist.ser.operation_jsons;

import lombok.Getter;

import java.util.List;

public class OperationJSON_without_acc {

    @Getter
    private long op_id;

    @Getter
    private long cat_id;

    @Getter
    private long timestamp;

    @Getter
    private double sum;

    @Getter
    private String memo;

    @Getter
    private String notes;

    @Getter
    private List<String> tags;

    @Getter
    private double balance;

    public OperationJSON_without_acc(long op_id, long cat_id, long timestamp, double sum, String memo, String notes, List<String> tags, double balance) {
        this.op_id = op_id;
        this.cat_id = cat_id;
        this.timestamp = timestamp;
        this.sum = sum;
        this.memo = memo;
        this.notes = notes;
        this.tags = tags;
        this.balance = balance;
    }
}
