package com.oms.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "reservation")
public class Reservation {
    private Long reservationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Space space;
    private User user;


    protected Reservation() {}
    public Reservation(LocalDate startDate, LocalDate endDate,
                       Space space, User user) {
        setStartDate(startDate);
        setEndDate(endDate);
        setSpace(space);
        setUser(user);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getReservationId() {
        return reservationId;
    }
    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }


    @Basic
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDateTime) {
        if(startDateTime == null) {
            throw new IllegalArgumentException("models.Reservation: Start date is null.");
        }
        this.startDate = startDateTime;
    }


    @Basic
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDateTime) {
        if(endDateTime == null) {
            throw new IllegalArgumentException("models.Reservation: End date is null.");
        }
        this.endDate = endDateTime;
    }


    @OneToOne
    public Space getSpace() {
        return space;
    }
    public void setSpace(Space space) {
        if(space == null) {
            throw new IllegalArgumentException("models.Reservation: Space is null.");
        }
        this.space = space;
    }


    @OneToOne
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("models.Reservation: User is null.");
        }
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", space=" + space +
                ", user=" + user +
                '}';
    }
}
