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
package ru.xidv.drankov.fassist.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xidv.drankov.fassist.dm.dao.SecToken;
import ru.xidv.drankov.fassist.dm.dao.User;
import ru.xidv.drankov.fassist.dm.dmr.TokenRepository;

import java.security.SecureRandom;

@Component
public class TokenGenerator {
    final TokenRepository tr;

    private final SecureRandom rnd = new SecureRandom();

    public SecToken gen(User user) {
        StringBuilder stringBuilder;
        do {
            stringBuilder = new StringBuilder("");
            String pattern = "ABCDEFGHIKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
            for (int i = 0; i < 32; i++) {
                stringBuilder.append(pattern.charAt(rnd.nextInt(pattern.length())));
            }
        } while (tr.findByToken(stringBuilder.toString()) != null);
        SecToken token = new SecToken(user, stringBuilder.toString());
        tr.save(token);
        return token;
    }

    @Autowired
    public TokenGenerator(TokenRepository tr) {
        this.tr = tr;
    }
}
