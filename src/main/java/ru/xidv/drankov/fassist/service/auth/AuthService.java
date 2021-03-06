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
package ru.xidv.drankov.fassist.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xidv.drankov.fassist.dm.dao.SecToken;
import ru.xidv.drankov.fassist.dm.dao.User;
import ru.xidv.drankov.fassist.dm.dmr.TokenRepository;

@Service
public class AuthService {

    private final TokenRepository tokenRepository;

    @Autowired
    public AuthService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public User getUserByToken(String secTokenString) throws Exception {
        SecToken byToken = tokenRepository.findByToken(secTokenString);

        if (byToken == null) {
            //not found
            throw new Exception("token " + secTokenString + " not found");

        }

        User user = byToken.getUser();

        if (user == null) {
            //not found
            throw new Exception("user not found");
        }

        return user;
    }
}
