package com.oms.controller;

import com.oms.DAO.IncomingEventDAO;
import com.oms.DAO.OutgoingEventDAO;
import com.oms.DAO.OutgoingUserDAO;
import com.oms.exception.ResourceNotFoundException;
import com.oms.model.Event;
import com.oms.model.User;
import com.oms.repository.EventRepository;
import com.oms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/getEvent/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable Long eventId) {
        Event foundEvent = eventRepository.findById(eventId).orElseThrow(() -> {
            throw new ResourceNotFoundException("controller.EventController: Event with ID not exists: " + eventId);
        });
        return ResponseEntity.ok(foundEvent);
    }

    @GetMapping("/{userId}")
    public List<OutgoingEventDAO> getEventsForUser(@PathVariable Long userId) {
        List<Event> foundEvents = eventRepository.getEventsUserParticipateIn(1L);
        List<OutgoingEventDAO> preparedEvents = new ArrayList<>();
        foundEvents.forEach(event -> {
            preparedEvents.add(new OutgoingEventDAO(
                    event.getEventId(),
                    event.getTitle(),
                    event.getStartDateTime(),
                    event.getDuration(),
                    event.getColor(),
                    event.getParticipants().size()
            ));
        });
        preparedEvents.sort(Comparator.comparing(OutgoingEventDAO::getStartDateTime));

        return preparedEvents;
    }

    @PostMapping("")
    ResponseEntity<Event> createEvent(@RequestBody IncomingEventDAO eventDAO) {
        User foundCreator = userRepository.findById(eventDAO.getCreatorId()).orElseThrow(() -> {
            throw new ResourceNotFoundException("controller.EventController: User with ID does not exist: " + eventDAO.getCreatorId());
        });
        List<User> foundParticipants = userRepository.findAllById(eventDAO.getParticipantsId());

        Event newEvent = new Event(
                eventDAO.getStartDateTime(),
                eventDAO.getDuration(),
                eventDAO.getColor(),
                foundCreator,
                foundParticipants,
                eventDAO.getTitle(),
                eventDAO.getRoomId()
        );

        eventRepository.save(newEvent);
        return ResponseEntity.ok(newEvent);
    }

    @PutMapping("/update/{eventId}")
    ResponseEntity<Event> updateEvent(@RequestBody IncomingEventDAO eventDAO) {
        Event foundEvent = eventRepository.findById(eventDAO.getEventId()).orElseThrow(() -> {
            throw new ResourceNotFoundException("controller.EventController: Event with ID does not exist: " + eventDAO.getCreatorId());
        });

        List<User> foundParticipants = userRepository.findAllById(eventDAO.getParticipantsId());

        foundEvent.setDuration(eventDAO.getDuration());
        foundEvent.setStartDateTime(eventDAO.getStartDateTime());
        foundEvent.setColor(eventDAO.getColor());
        foundEvent.setTitle(eventDAO.getTitle());
        foundEvent.setParticipants(foundParticipants);

        eventRepository.save(foundEvent);

        return ResponseEntity.ok(foundEvent);
    }

    @DeleteMapping("/delete/{eventId}")
    ResponseEntity<Event> deleteEvent(@PathVariable Long eventId) {
        Event foundEvent = eventRepository.findById(eventId).orElseThrow(() -> {
            throw new ResourceNotFoundException("controller.EventController: Event with ID does not exist: " + eventId);
        });
        eventRepository.delete(foundEvent);

        return ResponseEntity.ok(foundEvent);
    }
}
