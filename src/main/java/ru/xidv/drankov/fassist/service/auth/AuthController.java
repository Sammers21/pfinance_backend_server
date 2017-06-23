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

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.xidv.drankov.fassist.beans.TokenGenerator;
import ru.xidv.drankov.fassist.dm.dao.SecToken;
import ru.xidv.drankov.fassist.dm.dao.User;
import ru.xidv.drankov.fassist.dm.dmr.TokenRepository;
import ru.xidv.drankov.fassist.dm.dmr.UserRepository;
import ru.xidv.drankov.fassist.ser.shared_jsons.*;
import ru.xidv.drankov.fassist.service.category.CategoryService;


@RestController
@RequestMapping("/auth")
public class AuthController extends AbstractVerticle {

    private final Logger log = LoggerFactory.getLogger(AuthController.class.getName());

    private final TokenRepository tr;
    private final UserRepository ur;
    private final TokenGenerator tg;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    ResponseEntity<?> new_user(
            @RequestBody LogAndPassJSON logAndPassJSON
    ) {
        log.info("auth new call " + logAndPassJSON);
        //login existed case
        if (ur.findByLogin(logAndPassJSON.getLogin()) != null) {
            return new ResponseEntity<>(new ErrorCodeJSON(1), HttpStatus.OK);
        }

        ur.save(new User(logAndPassJSON));
        vertx.eventBus().publish("cat_service.new_user", logAndPassJSON.getLogin());

        return new ResponseEntity<>(new ErrorCodeJSON(0), HttpStatus.OK);
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    ResponseEntity<?> auth(
            @RequestBody LogAndPassJSON logAndPassJSON
    ) {
        log.info("auth auth call " + logAndPassJSON);
        User byLogin = ur.findByLogin(logAndPassJSON.getLogin());

        //login is not existed case
        if (byLogin == null ||
                !byLogin.getPassword().equals(logAndPassJSON.getPassword())) {
            return new ResponseEntity<>(new ErrorCodeAndMsgJSON(1, "invalid login or password"), HttpStatus.OK);
        }

        SecToken gen = tg.gen(byLogin);
        return new ResponseEntity<>(new ErrorCodeAndTokenJSON(0, gen), HttpStatus.OK);

    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    ResponseEntity<?> check(
            @RequestBody PureTokenJSON tok
    ) {
        log.info("auth check call " + tok);
        int code = tr.findByToken(tok.getToken()) == null ? 1 : 0;
        return new ResponseEntity<>(new ErrorCodeJSON(code), HttpStatus.OK);
    }

    @Autowired
    public AuthController(TokenRepository tr, UserRepository ur, TokenGenerator tg, CategoryService categoryService) {
        this.tr = tr;
        this.ur = ur;
        this.tg = tg;
    }
}

