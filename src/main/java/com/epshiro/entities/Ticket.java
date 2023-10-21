package com.epshiro.entities;

import jakarta.persistence.*;

@Entity
public class Ticket {

    private Integer number;

    @ManyToOne
    private Event event;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Ticket(Integer number, Event event) {
        this.number = number;
        this.event = event;
    }

    public Ticket() {

    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "number=" + number +
                ", event=" + event +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
