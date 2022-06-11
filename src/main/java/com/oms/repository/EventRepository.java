package com.oms.repository;

import com.oms.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT DISTINCT e FROM event e LEFT JOIN e.participants p WHERE p.userId = :userId OR e.creator.userId = :userId")
    public List<Event> getEventsUserParticipateIn(Long userId);

    @Query("SELECT e FROM event e WHERE e.creator.userId = :userId")
    public List<Event> getEventsUserCreated(Long userId);
}
