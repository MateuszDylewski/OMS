package com.oms.model;

import com.oms.enums.ParkingSize;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity(name = "Parking")
public class Parking extends Space {
    private ParkingSize size;


    protected Parking() {}
    public Parking(String name, int floor, ParkingSize size, String description, String type) {
        super(name, floor, description, type);
        setSize(size);
    }


    @Basic
    public ParkingSize getSize() {
        return size;
    }
    public void setSize(ParkingSize size) {
        if(size == null) {
            throw new IllegalArgumentException("models.Parking: Size is null.");
        }
        this.size = size;
    }
}
