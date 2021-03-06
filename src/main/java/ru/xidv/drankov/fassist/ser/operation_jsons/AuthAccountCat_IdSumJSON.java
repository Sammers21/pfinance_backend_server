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
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class AuthAccountCat_IdSumJSON {

    @Getter
    private String auth_token;

    @Getter
    private long account_id;

    @Getter
    private long category_id;

    @Getter
    private double sum;

    public AuthAccountCat_IdSumJSON(String auth_token, long account_id, long category_id, double sum) {
        this.auth_token = auth_token;
        this.account_id = account_id;
        this.category_id = category_id;
        this.sum = sum;
    }
}
