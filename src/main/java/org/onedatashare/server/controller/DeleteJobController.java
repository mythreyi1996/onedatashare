package org.onedatashare.server.controller;

import org.onedatashare.server.model.core.Job;
import org.onedatashare.server.model.core.ODSConstants;
import org.onedatashare.server.model.jobaction.JobRequest;
import org.onedatashare.server.model.requestdata.JobRequestData;
import org.onedatashare.server.model.useraction.UserAction;
import org.onedatashare.server.service.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/stork/deleteJob")
public class DeleteJobController {

    @Autowired
    private ResourceServiceImpl resourceService;

    @PostMapping
    public Mono<Job> restartJob(@RequestHeader HttpHeaders headers, @RequestBody JobRequestData jobRequestData){
        String cookie = headers.getFirst(ODSConstants.COOKIE);
        UserAction userAction = UserAction.convertToUserAction(jobRequestData);
        return resourceService.deleteJob(cookie, userAction);
    }
}