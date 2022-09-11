package com.meeting.service.impl;

import com.meeting.bo.Activities;
import com.meeting.service.MeetingSchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MeetingSchedulerServiceImpl implements MeetingSchedulerService {

    @Override
    public List<Activities> process(String searchFieldContent) {
        return null;
    }
}
