package com.meeting.ingtergration;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.meeting.MeetingSchedulerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MeetingSchedulerServiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void givenProcess_whenValid() throws Exception {
        String request = "0900 1730\n" +
                "2020-01-18 10:17:06 EMP001\n" +
                "2020-01-21 09:00 2\n" +
                "2020-01-18 12:34:56 EMP002\n" +
                "2020-01-21 09:00 2\n" +
                "2020-01-18 09:28:23 EMP003\n" +
                "2020-01-22 14:00 2\n" +
                "2020-01-18 11:23:45 EMP004\n" +
                "2020-01-22 16:00 1\n" +
                "2020-01-15 17:29:12 EMP005\n" +
                "2020-01-21 16:00 3\n" +
                "2020-01-18 11:00:45 EMP006\n" +
                "2020-01-23 16:00 1\n" +
                "2020-01-15 11:00:45 EMP007\n" +
                "2020-01-23 15:00 2\n" +
                "2020-01-18 11:23:45 EMP004\n" +
                "2020-01-20 12:00 1";
        ResponseEntity<List> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/meeting-scheduler/v1/bookings", request, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(4, response.getBody().size());
    }

    @Test
    public void givenProcess_whenInvalid() throws Exception {
        String request = " ";
        ResponseEntity<List> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/meeting-scheduler/v1/bookings", request, List.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void givenProcess_whenValidAndOutsideBusinessHour() throws Exception {
        String request = "0900 1730\n" +
                "2020-01-18 10:17:06 EMP001\n" +
                "2020-01-21 09:00 2\n" +
                "2020-01-18 12:34:56 EMP002\n" +
                "2020-01-21 09:00 2\n" +
                "2020-01-18 09:28:23 EMP003\n" +
                "2020-01-22 14:00 2\n" +
                "2020-01-18 11:23:45 EMP004\n" +
                "2020-01-22 16:00 1\n" +
                "2020-01-15 17:29:12 EMP005\n" +
                "2020-01-21 16:00 3\n" +
                "2020-01-18 11:00:45 EMP006\n" +
                "2020-01-23 16:00 1\n" +
                "2020-01-15 11:00:45 EMP007\n" +
                "2020-01-23 15:00 2\n" +
                "2020-01-18 11:23:45 EMP004\n" +
                "2020-01-20 19:00 1";
        ResponseEntity<List> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/meeting-scheduler/v1/bookings", request, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }
}
