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


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.xidv.drankov.fassist.dm.dmr.TokenRepository;
import ru.xidv.drankov.fassist.dm.dmr.UserRepository;

import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void basicAccountAndUserTest() {
    }

    @Test
    public void basicTokenAndUserTest() {
        User u = new User("somelog", "somepas");
        userRepository.save(u);
        Stream.of("l", "dqw", "weq")
                .forEach(s -> {
                    SecToken token = new SecToken(u, s + s);
                    tokenRepository.save(token);
                });

        //token exists
        assertTrue(tokenRepository.findByToken("ll") != null);
        assertTrue(tokenRepository.findByToken("dqwdqw") != null);
        assertTrue(tokenRepository.findByToken("weqweq") != null);

        //user exist
        assertTrue(userRepository.findByLogin("somelog").getPassword().equals("somepas"));

        //and has 3 operations
        assertTrue(userRepository.findByLogin("somelog").getTokens().size() == 3);

        //delete one of operations
        tokenRepository.delete(tokenRepository.findByToken("ll"));

        //user exist
        assertTrue(userRepository.findByLogin("somelog").getPassword().equals("somepas"));

        //and has 2 operations
        assertTrue(userRepository.findByLogin("somelog").getTokens().size() == 2);

        //delete other 2 operations
        tokenRepository.delete(tokenRepository.findByToken("dqwdqw"));
        tokenRepository.delete(tokenRepository.findByToken("weqweq"));

        //check user
        assertTrue(userRepository.findByLogin("somelog").getTokens().size() == 0);

        //delete user
        userRepository.delete(userRepository.findByLogin("somelog"));

        //check deleted user
        assertTrue(userRepository.findByLogin("somelog") == null);
    }
}
