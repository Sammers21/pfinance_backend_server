package ru.xidv.drankov.fassist.dm.dao;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.xidv.drankov.fassist.ser.shared_jsons.LogAndPassJSON;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Long id;

    @Getter
    @Column(name = "LOGIN", unique = true, nullable = false, length = 30)
    private String login;

    @Getter
    @Column(name = "PASSWORD", unique = false, nullable = false, length = 30)
    private String password;

    @Getter
    private String name;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Getter
    @Setter
    private Set<SecToken> tokens = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Getter
    @Setter
    private Set<Account> accounts = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "US_SET_ID")
    @Getter
    @Setter
    private UserSettings userSettings;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.name = login;
    }

    public User(LogAndPassJSON logAndPassJSON) {
        this.login = logAndPassJSON.getLogin();
        this.password = logAndPassJSON.getPassword();
        this.name = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getLogin().equals(user.getLogin())) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        return getName().equals(user.getName());
    }

    @Override
    public int hashCode() {
        int result = getLogin().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }
}
