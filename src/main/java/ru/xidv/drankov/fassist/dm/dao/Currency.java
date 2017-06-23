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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * ISO 4217 currency
 *
 * https://en.wikipedia.org/wiki/ISO_4217
 */
@NoArgsConstructor
@Entity(name = "Currency")
public class Currency {

    @Id
    @GeneratedValue
    @Column(name = "CUR_ID")
    private Long id;

    /**
     * 3 letter of currency as ISO4217 assumes
     *
     * For example: "EUR"
     */
    @Getter
    private String currency;

    public Currency(String currency) {
        this.currency = currency;
    }
}
