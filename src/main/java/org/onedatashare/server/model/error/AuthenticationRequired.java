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


package org.onedatashare.server.model.error;

import org.onedatashare.server.model.credential.OAuthCredential;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class AuthenticationRequired extends ODSError {
    /** Acceptable credential types. */
    public List<String> options;
    public OAuthCredential cred;
    /**
     * Authentication is required. Optionally, a list of allowed credential types
     * can be provided. These will be reported back to the client so it can
     * display authentication options.
     */
    public AuthenticationRequired(String... options) {
        super("Authentication is required.");
        type = "AuthenticationRequired";
        error = "Authentication is required.";
        status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (options != null && options.length > 0)
            this.options = Arrays.asList(options);
    }
}