package com.meeting.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingDetails implements Comparable<MeetingDetails> {
    private String empId;
    private LocalDateTime startDateTime;
    private LocalTime startTime;
    private LocalDateTime endDateTime;
    private LocalTime endTime;

    @Override
    public int compareTo(MeetingDetails meetingDetails) {
        if (startDateTime.isBefore(meetingDetails.getEndDateTime())
                && meetingDetails.getStartDateTime().isBefore(endDateTime)) {
            return 0;
        } else {
            return this.getStartDateTime().compareTo(meetingDetails.getStartDateTime());
        }
    }
}
