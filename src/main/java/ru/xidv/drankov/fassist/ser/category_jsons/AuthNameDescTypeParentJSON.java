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
package ru.xidv.drankov.fassist.ser.category_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthNameDescTypeParentJSON {

    @Getter
    private String auth_token;

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private long parent_id;

    public AuthNameDescTypeParentJSON(String auth_token, String name, String description, long parent_id) {
        this.auth_token = auth_token;
        this.name = name;
        this.description = description;
        this.parent_id = parent_id;
    }
}
