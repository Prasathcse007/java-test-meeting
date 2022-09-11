package com.ecom.exception;

public class MeetingSchedulerException extends Exception{
    public MeetingSchedulerException(String message) {
        super(message);
    }

    public MeetingSchedulerException(String message, Throwable cause) {
        super(message, cause);
    }
}
