package com.oms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    class Event {
        public Long id;
        public String description;
        public List<LocalDateTime> dates;
        public String color;

        public Event(Long id, String description, List<LocalDateTime> dates, String color) {
            this.id = id;
            this.description = description;
            this.dates = dates;
            this.color = color;
        }
    }

    @GetMapping()
    public List<Event> getEvents() {
        return Arrays.asList(
                new Event(
                        1L,
                        "Spotkanie z Adamem",
                        Arrays.asList(
                                LocalDateTime.now(),
                                LocalDateTime.now()
                                ),
                        "primary"
                ),
                new Event(
                        2L,
                        "Spotkanie z Adamem i Andrzejem poniatowskim na moście siekierkowskim",
                        Arrays.asList(
                                LocalDateTime.now().plusDays(8),
                                LocalDateTime.now().plusDays(8)
                        ),
                        "secondary"
                ),
                new Event(
                        3L,
                        "Spotkanie z Adamem i Andrzejem poniatowskim na moście siekierkowskim",
                        Arrays.asList(
                                LocalDateTime.now().plusDays(5),
                                LocalDateTime.now().plusDays(5)
                        ),
                        "info"
                ),
                new Event(
                        4L,
                        "Spotkanie z Adamem i Andrzejem poniatowskim na moście siekierkowskim",
                        Arrays.asList(
                                LocalDateTime.now().plusDays(1),
                                LocalDateTime.now().plusDays(2)
                        ),
                        "danger"
                ),
                new Event(
                        5L,
                        "Przerwa obiadowa - spotkanie z klientem",
                        Arrays.asList(
                                LocalDateTime.now(),
                                LocalDateTime.now()
                        ),
                        "warning"
                )

        );
    }
}


/*
  computed: {
          attributes() {
          return [
          ...this.events.map(event => ({
          dates: event.dates,
          key: event.id,
          customData: {
          title: event.description,
class: 'bg-'+event.color+' text-white',
        dates: {
        start: new Date(event.dates[0]).getHours()
        + ':'
        + (new Date(event.dates[0]).getMinutes() < 10 ? '0' : '')
        + new Date(event.dates[0]).getMinutes(),
        end: new Date(event.dates[1]).getHours()
        + ':'
        + (new Date(event.dates[1]).getMinutes() < 10 ? '0' : '')
        + new Date(event.dates[1]).getMinutes()
        },
        }
        }))
        ];
        },
        },*/
