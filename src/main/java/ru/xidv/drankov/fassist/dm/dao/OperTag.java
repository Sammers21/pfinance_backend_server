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

@Entity(name = "OperTag")
@NoArgsConstructor
public class OperTag {

    @Id
    @GeneratedValue
    @Column(name = "TAG_ID")
    @Getter
    private Long id;


    @Column(length = 50)
    @Getter
    private String tag;

    @ManyToOne
    @JoinColumn(name = "OP_ID")
    @Getter
    private Operation operation;

    public OperTag(String tag, Operation operation) {
        this.tag = tag;
        this.operation = operation;
    }
}
