package com.oms.DAO;

import java.time.LocalDateTime;

public class OutgoingEventDAO {
    private Long eventId;
    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int duration;
    private String color;
    private int countParticipants;

    protected OutgoingEventDAO() {}

    public OutgoingEventDAO(Long eventId, String title, LocalDateTime startDateTime,
                            int duration, String color,
                            int countParticipants) {
        this.eventId = eventId;
        this.title = title;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.color = color;
        this.countParticipants = countParticipants;
        this.endDateTime = startDateTime.plusMinutes(duration);
    }

    public OutgoingEventDAO(Long eventId, String title, LocalDateTime startDateTime,
                            int duration, String color) {
        this.eventId = eventId;
        this.title = title;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.color = color;
        this.endDateTime = startDateTime.plusMinutes(duration);
    }

    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public int getCountParticipants() {
        return countParticipants;
    }
    public void setCountParticipants(int countParticipants) {
        this.countParticipants = countParticipants;
    }
}
