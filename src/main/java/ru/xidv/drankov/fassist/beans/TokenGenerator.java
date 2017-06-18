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
