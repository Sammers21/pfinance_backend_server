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

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@NoArgsConstructor
@Entity(name = "\"CurRate\"")
public class CurRate {

    @EmbeddedId
    @Getter
    private Timestamp timestamp;


    /**
     * cur1_id — Валюта один (как правило, "внешняя", не домашняя)
     */
    @ManyToOne
    @JoinColumn(name = "CUR_ID", insertable = false, updatable = false)
    @Getter
    private Currency currency_1;


    /**
     * cur2_id — Валюта , как правило, "домашняя"
     */
    @ManyToOne
    @JoinColumn(name = "CUR_ID", insertable = false, updatable = false)
    @Getter
    private Currency currency_2;

    /**
     * rate = cur2/cur1 — если cur1 — это $, а cur2 — руб.,
     * тогда rate = cur2/cur1 = 58 (по состоянию на 24.03.2017)
     * — см. размерность, которая получается при делении!!!
     */
    @Getter
    private double rate;

    public CurRate(Timestamp timestamp, Currency currency_1, Currency currency_2, double rate) {
        this.timestamp = timestamp;
        this.currency_1 = currency_1;
        this.currency_2 = currency_2;
        this.rate = rate;
    }
}
