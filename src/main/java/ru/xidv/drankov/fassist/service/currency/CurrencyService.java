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
package ru.xidv.drankov.fassist.service.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.xidv.drankov.fassist.dm.dao.Currency;
import ru.xidv.drankov.fassist.dm.dmr.CurrencyRepository;

@Service
public class CurrencyService {


    private final CurrencyRepository currencyRepository;

    public Currency getCurrency(String currency) throws Exception {
        Currency byISO_4217_3_letters_string = currencyRepository.findByCurrency(currency);
        if (byISO_4217_3_letters_string == null) {
            throw new Exception("currency not found");
        }

        return byISO_4217_3_letters_string;
    }


    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    /**
     * Check either or not this two currency in DB
     */
    @Bean
    CommandLineRunner cmd(CurrencyRepository currencyRepository) {
        return si -> {
            if (currencyRepository.findByCurrency("EUR") == null) {
                currencyRepository.save(new Currency("EUR"));
            }
            if (currencyRepository.findByCurrency("RUB") == null) {
                currencyRepository.save(new Currency("RUB"));
            }
        };
    }
}
