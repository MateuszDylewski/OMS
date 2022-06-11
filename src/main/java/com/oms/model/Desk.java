package com.oms.model;

import javax.persistence.Entity;

@Entity(name = "Desk")
public class Desk extends Space{


    protected Desk() {}
    public Desk(String name, int floor, String description, String type) {
        super(name, floor, description, type);
    }

    @Override
    public String toString() {
        return "Desk{" + super.toString() + "}";
    }
}
