package com.meeting.service;


import com.meeting.bo.Activities;

import java.util.List;

public interface MeetingSchedulerService {

    List<Activities> process(String meetings);
}
