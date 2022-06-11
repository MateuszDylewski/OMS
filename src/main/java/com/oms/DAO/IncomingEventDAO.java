package com.oms.DAO;

import java.time.LocalDateTime;
import java.util.List;

public class IncomingEventDAO {
    private Long eventId;
    private String title;
    private LocalDateTime startDateTime;
    private int duration;
    private String color;
    private Long creatorId;
    private List<Long> participantsId;

    private String roomId;

    protected IncomingEventDAO() {}
    public IncomingEventDAO(String title, LocalDateTime startDateTime,
                            int duration, String color, Long creatorId,
                            List<Long> participantsId, String roomId) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.color = color;
        this.creatorId = creatorId;
        this.participantsId = participantsId;
        this.roomId = roomId;
    }

    public IncomingEventDAO(Long eventId, String title, LocalDateTime startDateTime,
                            int duration, String color, Long creatorId,
                            List<Long> participantsId, String roomId) {
        this.eventId = eventId;
        this.title = title;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.color = color;
        this.creatorId = creatorId;
        this.participantsId = participantsId;
        this.roomId = roomId;
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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public List<Long> getParticipantsId() {
        return participantsId;
    }

    public void setParticipantsId(List<Long> participantsId) {
        this.participantsId = participantsId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
