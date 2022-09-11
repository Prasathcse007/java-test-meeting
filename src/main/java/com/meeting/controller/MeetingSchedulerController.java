package com.meeting.controller;


import com.meeting.bo.Activities;
import com.meeting.service.MeetingSchedulerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/v1")
public class MeetingSchedulerController {

    @Autowired
    private MeetingSchedulerService meetingSchedulerService;

    @GetMapping(value = "/")
    public String index() {
        return "Alive";
    }

    @PostMapping(value = "/bookings")
    @ApiOperation(value = "/bookings")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    public ResponseEntity<List<Activities>> order(@RequestBody String searchFieldContent) {
        return new ResponseEntity<>(meetingSchedulerService.process(searchFieldContent), HttpStatus.OK);
    }
}
