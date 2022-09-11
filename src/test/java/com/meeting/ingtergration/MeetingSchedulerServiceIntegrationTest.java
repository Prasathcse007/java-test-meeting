package com.meeting.ingtergration;


import com.meeting.bo.Activities;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.meeting.MeetingSchedulerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MeetingSchedulerServiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void givenProcess_whenAccept() throws Exception {
        /*String request = "";ParameterizedTypeReference<List<Activities>> responseType = new ParameterizedTypeReference<List<Activities>>(){};
        ResponseEntity<List<Activities>> response = this.restTemplate
                .exchange("http://localhost:" + port + "/meeting-scheduler/v1/bookings", HttpMethod.POST, request, responseType);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(3, response.getBody().size());*/
    }
}
