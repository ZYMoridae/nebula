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

package com.jz.nebula.controller;

import com.jz.nebula.component.exception.payment.InvalidPaymentTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    /**
     * FIXME: This is the demo for the error handling, we need to map all the errors and put into the db. In addition, all the error should be put into db for further analyse
     *
     * @param ex
     * @param request
     *
     * @return
     */
    @ExceptionHandler(value
            = {InvalidPaymentTokenException.class})
    protected ResponseEntity<Object> handleConflict(
            Exception ex, WebRequest request) {
        HashMap bodyOfResponse = new HashMap();

        bodyOfResponse.put("message", "Invalid payment token");
        bodyOfResponse.put("internalRefId", "ICDERR0000001");

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}

