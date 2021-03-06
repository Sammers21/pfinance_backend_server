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

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class VertxProcessor implements BeanPostProcessor {

    @Bean
    public Vertx deployer() {
        return Vertx.vertx();
    }

    @Autowired
    private Vertx vertx;

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    /**
     * Here we deploy each class as Verticle if it was extended by {@code AbstractVerticle.class}
     */
    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (AbstractVerticle.class.isAssignableFrom(o.getClass())) {
            vertx.deployVerticle((AbstractVerticle) o);
        }
        return o;
    }
}
