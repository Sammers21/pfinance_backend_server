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
