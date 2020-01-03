/*
 * Copyright (c) 2019. Nebula Technology
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

package com.jz.nebula.controller.api;

import com.jz.nebula.entity.CurrencyRate;
import com.jz.nebula.service.CurrencyRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/currency-rates")
public class CurrencyController {

    @Autowired
    CurrencyRatesService currencyRatesService;

    @GetMapping("")
    public Object getCurrencies(@RequestParam String baseCurrency, Pageable pageable,
                                final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                PagedResourcesAssembler<CurrencyRate> assembler) {
        return currencyRatesService.findAll(baseCurrency, pageable, assembler);
    }

}
