/**
 ##**************************************************************
 ##
 ## Copyright (C) 2018-2020, OneDataShare Team, 
 ## Department of Computer Science and Engineering,
 ## University at Buffalo, Buffalo, NY, 14260.
 ## 
 ## Licensed under the Apache License, Version 2.0 (the "License"); you
 ## may not use this file except in compliance with the License.  You may
 ## obtain a copy of the License at
 ## 
 ##    http://www.apache.org/licenses/LICENSE-2.0
 ## 
 ## Unless required by applicable law or agreed to in writing, software
 ## distributed under the License is distributed on an "AS IS" BASIS,
 ## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ## See the License for the specific language governing permissions and
 ## limitations under the License.
 ##
 ##**************************************************************
 */


package org.onedatashare.server.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailServiceTest {

    EmailService emailService;

    String validInputEmail = "ods_test_user@test.com";
    String invalidInputEmail = "invalidEmail";

    @Before
    public void setup() {
        this.emailService = new EmailService();
    }

    @Test
    @DisplayName("Valid email")
    public void isValidEmail(){
        assertEquals(true,emailService.isValidEmail(validInputEmail),"Invalid email id");
    }

    @Test
    @DisplayName("InValid email")
    public void isInValidEmail(){
        assertEquals(false,emailService.isValidEmail(invalidInputEmail),"valid email id");
    }
}
