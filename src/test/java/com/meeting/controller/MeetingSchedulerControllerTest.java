package com.meeting.controller;

import com.meeting.bo.Activities;
import com.meeting.service.MeetingSchedulerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
@RunWith(MockitoJUnitRunner.class)
public class MeetingSchedulerControllerTest {
    @InjectMocks
    private MeetingSchedulerController meetingSchedulerController;

    @Mock
    private MeetingSchedulerService meetingSchedulerService;

    @Before
    public void before (){
        meetingSchedulerController = new MeetingSchedulerController(meetingSchedulerService);
    }

    @Test
    public void givenProcess_whenValid() throws Exception {
        String meeting = "0900 1730\n" +
                "2020-01-18 10:17:06 EMP001\n" +
                "2020-01-21 09:00 2\n";
        Mockito.when(meetingSchedulerService.process(Mockito.anyString())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Activities>> responseEntity = meetingSchedulerController.process(meeting);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void givenProcess_whenInValid() throws Exception {
        String meeting = " ";
        Mockito.when(meetingSchedulerService.process(Mockito.anyString())).thenReturn(null);
        ResponseEntity<List<Activities>> responseEntity = meetingSchedulerController.process(meeting);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
