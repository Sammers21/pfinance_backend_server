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
