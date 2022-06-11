package com.oms.model;

import javax.persistence.*;

@Entity(name = "Device")
public class Device {
    private Long deviceId;
    private String name;
    private String details;
    private Desk desk;

    protected Device() {}
    public Device(String name, String details, Desk desk) {
        setName(name);
        setDetails(details);
        setDesk(desk);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(Long id) {
        this.deviceId = id;
    }


    @Basic
    public String getName() {
        return name;
    }
    public void setName(String name) {
        if(name == null || name.length() <= 0) {
            throw new IllegalArgumentException("models.Device: Name is null or empty.");
        }
        this.name = name;
    }


    @Basic
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }


    @ManyToOne
    public Desk getDesk() {
        return desk;
    }
    public void setDesk(Desk desk) {
        this.desk = desk;
    }
}
