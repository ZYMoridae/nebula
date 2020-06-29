/*
 * Copyright (c) 2019. iEuclid Technology
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.jz.nebula.service;

import com.google.common.base.Strings;
import com.jz.nebula.controller.api.ProductController;
import com.jz.nebula.dao.CurrencyRateRepository;
import com.jz.nebula.entity.CurrencyApiResponse;
import com.jz.nebula.entity.CurrencyRate;
import com.jz.nebula.entity.product.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class CurrencyRatesService {
    private final static Logger logger = LogManager.getLogger(CurrencyRatesService.class);

    @Autowired
    CurrencyRateRepository currencyRateRepository;

    /**
     * Find all currency rates by base currency
     *
     * @param baseCurrency
     * @param pageable
     * @param assembler
     *
     * @return
     */
    public PagedModel<EntityModel<CurrencyRate>> findAll(String baseCurrency, Pageable pageable,
                                                         PagedResourcesAssembler<CurrencyRate> assembler) {
        Page<CurrencyRate> page;
        if (Strings.isNullOrEmpty(baseCurrency)) {
            page = currencyRateRepository.findAllByOrderByIdAsc(pageable);
        } else {
            page = currencyRateRepository.findByBaseCurrencyOrderByCurrencyCodeAsc(baseCurrency, pageable);
        }

        PagedModel<EntityModel<CurrencyRate>> resources = assembler.toModel(page,
                linkTo(ProductController.class).slash("/currency-rates").withSelfRel());

        return resources;
    }

    public CurrencyRate save(CurrencyRate currencyRate) {
        return currencyRateRepository.save(currencyRate);
    }

    /**
     * Pull currency rate from exchange rate api
     */
    public void syncCurrencyRate() {
        String[] allRates = new String[]{"CAD", "HKD", "ISK", "PHP", "DKK", "HUF", "CZK", "GBP", "RON", "SEK", "IDR", "INR", "BRL", "RUB", "HRK", "JPY", "THB", "CHF", "EUR", "MYR", "BGN", "TRY", "CNY", "NOK", "NZD", "ZAR", "USD", "MXN", "SGD", "AUD", "ILS", "KRW", "PLN"};
        for (String rate : allRates) {
            this.syncRateByBaseCurrency(rate);
        }
    }

    /**
     * Pulling currency rate with particular base currency
     *
     * @param baseCurrency
     */
    public void syncRateByBaseCurrency(String baseCurrency) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.exchangeratesapi.io/latest?base=" + baseCurrency;

        CurrencyApiResponse response = restTemplate
                .getForObject(url, CurrencyApiResponse.class);

        Set<String> keySet = response.getRates().keySet();

        for (String key : keySet
        ) {
            Optional<CurrencyRate> optionalCurrencyRate = currencyRateRepository.findByCurrencyCodeAndBaseCurrency(key.toUpperCase(), baseCurrency);
            if (optionalCurrencyRate.isPresent()) {
                logger.debug("findCurrency:: [{}][{}]", key, baseCurrency);
                CurrencyRate currencyRate = optionalCurrencyRate.get();
                double currentRate = response.getRates().get(key);
                if (currencyRate.getRate() != currentRate) {
                    currencyRate.setRate(currentRate);
                    currencyRateRepository.save(currencyRate);
                }
            } else {
                CurrencyRate currencyRate = new CurrencyRate();
                currencyRate.setBaseCurrency(baseCurrency);
                currencyRate.setCurrencyCode(key.toUpperCase());
                currencyRate.setRate(response.getRates().get(key));
                currencyRateRepository.save(currencyRate);
            }


        }
    }

}
