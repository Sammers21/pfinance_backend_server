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

import javax.persistence.*;

@Entity(name = "\"UserSettings\"")
@NoArgsConstructor
public class UserSettings {

    @Id
    @GeneratedValue
    @Getter
    @Column(name = "US_SET_ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    @Getter
    private User user;


    @OneToOne
    @JoinColumn(name = "CUR_ID")
    @Getter
    private Currency currency;

    public UserSettings(User user, Currency currency) {
        this.user = user;
        this.currency = currency;
    }
}


