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
