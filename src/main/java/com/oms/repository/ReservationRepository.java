package com.oms.repository;

import com.oms.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM reservation r WHERE r.space.spaceId = :spaceId")
    List<Reservation> findReservationForSpace(Long spaceId);

    @Query("SELECT r FROM reservation r WHERE r.user.userId = :userId")
    List<Reservation> findReservationsByUser(Long userId);
}
