package com.meeting.service;

import com.meeting.bo.Activities;
import com.meeting.service.impl.MeetingSchedulerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class MeetingSchedulerServiceTest {

    @InjectMocks
    private MeetingSchedulerServiceImpl meetingSchedulerService;

    @Before
    public void before() {
        meetingSchedulerService = new MeetingSchedulerServiceImpl();
    }

    @Test
    public void givenProcess_whenValid() {
        String meeting = "0900 1730\n" +
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

        List<Activities> bookings = meetingSchedulerService.process(meeting);
        assertEquals(3, bookings.size());
        assertEquals(LocalDate.parse("2020-01-23"), bookings.get(0).getData());
        assertEquals(1, bookings.get(0).getBookings().size());
        assertEquals(LocalDate.parse("2020-01-22"), bookings.get(1).getData());
        assertEquals(2, bookings.get(1).getBookings().size());
        assertEquals(LocalDate.parse("2020-01-21"), bookings.get(2).getData());
        assertEquals(1, bookings.get(2).getBookings().size());
    }

    @Test
    public void givenProcess_whenInvalid() throws Exception {
        assertNull(meetingSchedulerService.process(" "));
    }

    @Test
    public void givenProcess_whenOutsideBusinessHour() throws Exception {
        String meeting = "0900 1730\n" +
                "2020-01-18 11:23:45 EMP004\n" +
                "2020-01-20 19:00 1";

        List<Activities> bookings = meetingSchedulerService.process(meeting);
        assertEquals(0, bookings.size());
    }

    @Test
    public void givenProcess_whenValidAndOutsideBusinessHour() throws Exception {
        String meeting = "0900 1730\n" +
                "2020-01-18 11:00:45 EMP006\n" +
                "2020-01-23 16:00 1\n" +
                "2020-01-18 11:23:45 EMP004\n" +
                "2020-01-20 19:00 1";

        List<Activities> bookings = meetingSchedulerService.process(meeting);
        assertEquals(1, bookings.size());
        assertEquals(LocalDate.parse("2020-01-23"), bookings.get(0).getData());
        assertEquals(1, bookings.get(0).getBookings().size());
    }

    @Test
    public void givenProcess_whenOnlyOfficeHours() throws Exception {
        String meeting = "0900 1730";
        List<Activities> bookings = meetingSchedulerService.process(meeting);
        assertEquals(0, bookings.size());
    }
}
