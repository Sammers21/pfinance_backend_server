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
