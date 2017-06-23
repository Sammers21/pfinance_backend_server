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
