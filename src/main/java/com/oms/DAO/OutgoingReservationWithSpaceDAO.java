package com.oms.DAO;

import com.oms.model.Space;

import java.time.LocalDate;

public class OutgoingReservationWithSpaceDAO {
    private Long reservationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Space space;

    public OutgoingReservationWithSpaceDAO(Long reservationId, LocalDate startDate, LocalDate endDate, Space space) {
        this.reservationId = reservationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.space = space;
    }

    public Long getReservationId() {
        return reservationId;
    }
    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Space getSpace() {
        return space;
    }
    public void setSpace(Space space) {
        this.space = space;
    }

    public String getSpaceName(){
        return space.getName();
    }

    public Boolean isSpaceBlocked() {
        return space.isBlocked();
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


}
