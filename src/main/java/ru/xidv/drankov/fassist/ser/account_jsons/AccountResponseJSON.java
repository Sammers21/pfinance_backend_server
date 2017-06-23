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

@NoArgsConstructor
public class AccountResponseJSON {

    @Getter
    private String name;

    @Getter
    private int type;

    @Getter
    private long account_id;

    @Getter
    private String currency;

    @Getter
    private double balance;

    @Getter
    private long opendate;

    @Getter
    private long closedate;

    public AccountResponseJSON(String name, int type, long account_id, String currency, double balance, long opendate, long closedate) {
        this.name = name;
        this.type = type;
        this.account_id = account_id;
        this.currency = currency;
        this.balance = balance;
        this.opendate = opendate;
        this.closedate = closedate;
    }
}
