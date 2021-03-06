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
package ru.xidv.drankov.fassist.dm.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xidv.drankov.fassist.ser.account_jsons.AccountResponseJSON;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity(name = "account")
public class Account {


    @Id
    @GeneratedValue
    @Column(name = "AC_ID")
    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    private Integer type;

    @Getter
    private long opendate;

    @Setter
    @Getter
    private long  closedate;


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @Getter
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    @Getter
    @Setter
    private Set<Operation> operations = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "CUR_ID")
    @Getter
    private Currency currency;

    public Account(String name, Integer type, long  opendate, User user, Currency currency) {
        this.name = name;
        this.type = type;
        this.opendate = opendate;
        this.user = user;
        this.currency = currency;
        closedate=0;
    }

    public AccountResponseJSON get_as_json() {
        double balance;
        if (operations.isEmpty()) {
            balance = 0;
        } else {
            balance = operations
                    .parallelStream()
                    .max(Comparator.comparingLong(Operation::getTimestamp))
                    .get()
                    .getBalance();
        }

        return new AccountResponseJSON(
                name,
                type,
                id,
                currency.getCurrency(),
                balance,
                opendate,
                closedate
        );
    }
}
