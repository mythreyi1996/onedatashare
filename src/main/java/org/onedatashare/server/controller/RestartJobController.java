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


package org.onedatashare.server.controller;

import org.onedatashare.server.model.core.Job;
import org.onedatashare.server.model.core.ODSConstants;
import org.onedatashare.server.model.request.JobRequestData;
import org.onedatashare.server.model.useraction.UserAction;
import org.onedatashare.server.service.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controller for handling Restart Job Requests
 */
@RestController
@RequestMapping("/api/stork/restart")
public class RestartJobController {

    @Autowired
    private ResourceServiceImpl resourceService;

    /**
     * Handler for requests of restart Job
     * @param headers - Incoming request headers
     * @param jobRequestData - Request data with Job details
     * @return Mono\<Job\>
     */
    @PostMapping
    public Mono<Job> restartJob(@RequestHeader HttpHeaders headers, @RequestBody JobRequestData jobRequestData){
        String cookie = headers.getFirst(ODSConstants.COOKIE);
        UserAction userAction = UserAction.convertToUserAction(jobRequestData);
        return resourceService.restartJob(cookie, userAction);
    }
}
