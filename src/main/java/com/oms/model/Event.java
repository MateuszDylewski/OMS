package com.oms.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "event")
public class Event {

    private Long eventId;
    private String title;
    private LocalDateTime startDateTime;
    private int duration;
    private String color;
    private User creator;
    private List<User> participants;


    protected Event() {}
    public Event(LocalDateTime startDateTime, int duration, String color, User creator, List<User> participants, String title) {
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.color = color;
        this.creator = creator;
        this.participants = participants;
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Basic
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Basic
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Basic
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    @OneToOne
    public User getCreator() {
        return creator;
    }
    public void setCreator(User creator) {
        this.creator = creator;
    }

    @ManyToMany()
    public List<User> getParticipants() {
        return participants;
    }
    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    @Basic
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", startDateTime=" + startDateTime +
                ", duration=" + duration +
                ", color='" + color + '\'' +
                ", creator=" + creator +
                ", participants=" + participants +
                '}';
    }
}
