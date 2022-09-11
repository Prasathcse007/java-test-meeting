package com.meeting.controller;


import com.meeting.bo.Activities;
import com.meeting.service.MeetingSchedulerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/v1")
public class MeetingSchedulerController {

    private final MeetingSchedulerService meetingSchedulerService;

    public MeetingSchedulerController(MeetingSchedulerService meetingSchedulerService) {
        this.meetingSchedulerService = meetingSchedulerService;
    }

    @GetMapping(value = "/")
    public String index() {
        return "Alive";
    }

    /**
     * Process the bulk meeting details
     *
     * @param meetings
     * @return
     */
    @PostMapping(value = "/bookings")
    @ApiOperation(value = "/bookings")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    public ResponseEntity<List<Activities>> process(@RequestBody String meetings) {
        List<Activities> activities = meetingSchedulerService.process(meetings);
        if (Objects.isNull(activities)) {
            return new ResponseEntity<>(activities, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(activities, HttpStatus.OK);
        }

    }
}
