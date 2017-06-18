package ru.xidv.drankov.fassist.dm.dao;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Table(name = "\"SecToken\"")
@EqualsAndHashCode
public class SecToken {

    @Id
    @GeneratedValue
    @Column(name = "TOKEN_ID", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @Getter
    private User user;


    @Column(name="nv_after")
    private Timestamp nvAfter;

    @Column(name="nv_before")
    private Timestamp nvBefore;


    @Getter
    @Column(name = "TOKEN", unique = true, nullable = false)
    private String token;

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", user=" + user.getLogin() +
                ", token='" + token + '\'' +
                '}';
    }

    public SecToken(User user, String token) {
        this.user = user;
        this.token = token;
    }

}
