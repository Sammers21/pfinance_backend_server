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
