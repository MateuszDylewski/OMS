package com.oms.DAO;

import java.time.LocalDate;

public class OutgoingReservationDAO {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long spaceId;

    public OutgoingReservationDAO(LocalDate startDate, LocalDate endDate, Long spaceId) {
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Long getSpaceId() {
        return spaceId;
    }
    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }
}
