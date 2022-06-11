package com.oms.DAO;

import java.time.LocalDate;

public class IncomingReservationDAO {
    private Long spaceId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;

    public IncomingReservationDAO(Long spaceId, LocalDate startDate, LocalDate endDate, Long userId) {
        this.spaceId = spaceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "IncomingReservationDAO{" +
                "spaceId=" + spaceId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", userId=" + userId +
                '}';
    }
}
