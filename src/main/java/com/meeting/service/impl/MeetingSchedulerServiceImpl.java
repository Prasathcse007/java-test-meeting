package com.meeting.service.impl;

import com.meeting.bo.Activities;
import com.meeting.bo.Meeting;
import com.meeting.bo.MeetingDetails;
import com.meeting.bo.Meetings;
import com.meeting.service.MeetingSchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MeetingSchedulerServiceImpl implements MeetingSchedulerService {
    public static final String NEWLINE = "\n";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Process the bulk meeting details
     *
     * @param meetings
     * @return
     */
    @Override
    public List<Activities> process(String meetings) {
        try {
            if (StringUtils.isBlank(meetings)) {
                log.error("Invalid input, please correct the input.");
                return null;
            }
            Meetings activities = processMeeting(meetings);
            return activities.getMeetings().keySet().stream().map(localDate ->
                    Activities.builder().data(localDate).bookings(getMeetings(activities.getMeetings().get(localDate)))
                            .build()).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Exception in process", ex);
        }
        return null;
    }

    /**
     * Mapping the response format
     *
     * @param details
     * @return
     */
    private Set<Meeting> getMeetings(Set<MeetingDetails> details) {
        return details.stream().map(meetingDetails -> Meeting.builder().emp_id(meetingDetails.getEmpId())
                .start_time(meetingDetails.getStartTime()).end_time(meetingDetails.getEndTime())
                .build()).collect(Collectors.toSet());
    }

    /**
     * Process the message, validate and convert
     *
     * @param meetings
     * @return
     */
    private Meetings processMeeting(String meetings) {
        try {
            String[] data = meetings.split(NEWLINE);
            String[] officeHours = data[0].split(" ");
            LocalTime officeStartTime = LocalTime.of(Integer.parseInt(officeHours[0].substring(0, 2))
                    , Integer.parseInt(officeHours[0].substring(2, 4)));
            LocalTime officeFinishTime = LocalTime.of(Integer.parseInt(officeHours[1].substring(0, 2))
                    , Integer.parseInt(officeHours[1].substring(2, 4)));

            Map<LocalDate, Set<MeetingDetails>> meetingList = getMeetingList(data, officeStartTime, officeFinishTime);
            return new Meetings(officeStartTime, officeFinishTime,
                    meetingList);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Iterate the meeting details and convert into meeting
     *
     * @param data
     * @param officeStartTime
     * @param officeFinishTime
     * @return
     */
    private Map<LocalDate, Set<MeetingDetails>> getMeetingList(String[] data, LocalTime officeStartTime, LocalTime officeFinishTime) {
        Map<LocalDate, Set<MeetingDetails>> meetingList = new HashMap<LocalDate, Set<MeetingDetails>>();

        for (int i = 1; i < data.length; i = i + 2) {
            String[] meetingSlot = data[i + 1].split(" ");
            LocalDate meetingDate = LocalDate.parse(meetingSlot[0], dateFormatter);
            MeetingDetails meetingDetails = extractMeeting(data[i], meetingSlot, officeStartTime, officeFinishTime);
            if (Objects.nonNull(meetingDetails)) {
                if (meetingList.containsKey(meetingDate)) {
                    meetingList.get(meetingDate).add(meetingDetails);
                } else {
                    Set<MeetingDetails> meetingsForDay = new TreeSet<MeetingDetails>();
                    meetingsForDay.add(meetingDetails);
                    meetingList.put(meetingDate, meetingsForDay);
                }
            }
        }
        return meetingList;
    }

    /**
     * Create the Meeting Object from string
     *
     * @param data
     * @param meetingSlot
     * @param officeStartTime
     * @param officeEndTime
     * @return
     */
    private MeetingDetails extractMeeting(String data, String[] meetingSlot, LocalTime officeStartTime
            , LocalTime officeEndTime) {
        String[] bookingDetailLine = data.split(" ");
        String emplId = bookingDetailLine[2];
        LocalDateTime meetingStartDateTime = LocalDateTime.parse(getDateTime(meetingSlot), dateTimeFormatter);
        LocalTime meetingStartTime = LocalTime.parse(meetingSlot[1], timeFormatter);
        LocalDateTime meetingEndDateTime = meetingStartDateTime.plusHours(Integer.parseInt(meetingSlot[2]));
        LocalTime meetingEndTime = meetingStartTime.plusHours(Integer.parseInt(meetingSlot[2]));

        if (!validateBusinessHour(officeStartTime, officeEndTime, meetingStartTime, meetingEndTime)) {
            return MeetingDetails.builder().empId(emplId).startDateTime(meetingStartDateTime)
                    .startTime(meetingStartTime).endDateTime(meetingEndDateTime).endTime(meetingEndTime).build();
        } else {
            log.warn("Invalid Booking {} - {}", data, meetingSlot);
            return null;
        }
    }

    /**
     * Form the datetime
     *
     * @param meetingSlot
     * @return
     */
    private StringBuilder getDateTime(String[] meetingSlot) {
        return new StringBuilder(meetingSlot[0]).append(" ").append(meetingSlot[1]);
    }

    /**
     * Check the meeting inside the business hours
     *
     * @param officeStartTime
     * @param officeFinishTime
     * @param meetingStartTime
     * @param meetingFinishTime
     * @return
     */
    private boolean validateBusinessHour(LocalTime officeStartTime,
                                         LocalTime officeFinishTime, LocalTime meetingStartTime,
                                         LocalTime meetingFinishTime) {
        return meetingStartTime.isBefore(officeStartTime)
                || meetingStartTime.isAfter(officeFinishTime)
                || meetingFinishTime.isAfter(officeFinishTime)
                || meetingFinishTime.isBefore(officeStartTime);
    }
}
