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

@Entity(name = "\"operattachment\"")
@NoArgsConstructor
public class OperAttach {
    @Id
    @GeneratedValue
    @Column(name = "OP_AT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OP_ID")
    @Getter
    private Operation operation;

    @Column(length = 255)
    private String url;

    public OperAttach(Operation operation, String url) {
        this.operation = operation;
        this.url = url;
    }
}
