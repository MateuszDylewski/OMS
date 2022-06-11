package com.oms.model;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity(name = "Room")
public class Room extends Space {
    private int allowedAttendees;
    private int area;


    protected Room() {}
    public Room(String name, int floor, int allowedAttendees, int area, String description, String type) {
        super(name, floor, description, type);
        setAllowedAttendees(allowedAttendees);
        setArea(area);
    }


    @Basic
    public int getAllowedAttendees() {
        return allowedAttendees;
    }
    public void setAllowedAttendees(int allowedAttendees) {
        if(allowedAttendees < 0){
            throw new IllegalArgumentException("models.Room: Allowed attendees is lower than 0.");
        }
        this.allowedAttendees = allowedAttendees;
    }


    @Basic
    public int getArea() {
        return area;
    }
    public void setArea(int area) {
        if(area < 0) {
            throw new IllegalArgumentException("models.Room: Area is lower than 0.");
        }
        this.area = area;
    }
}
