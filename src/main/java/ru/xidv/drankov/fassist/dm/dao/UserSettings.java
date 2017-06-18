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


