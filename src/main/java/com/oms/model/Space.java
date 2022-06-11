package com.oms.model;

import com.oms.exception.InvalidUserRoleException;

import javax.persistence.*;

@Entity(name = "Space")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Space {
    private Long spaceId;
    private String name;
    private int floor;
    private String description;
    private boolean isBlocked;
    private String type;


    protected Space() {}
    public Space(String name, int floor, String description, String type) {
        setName(name);
        setFloor(floor);
        setDescription(description);
        setBlocked(false);
        setType(type);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getSpaceId() {
        return spaceId;
    }
    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }


    @Basic
    public String getName() {
        return name;
    }
    public void setName(String name) {
        if(name == null || name.length() <= 0) {
            throw new IllegalArgumentException("models.Space: Name is null or empty.");
        }
        this.name = name;
    }


    @Basic
    public int getFloor() {
        return floor;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Basic
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    public boolean isBlocked() {
        return isBlocked;
    }
    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Basic
    public String getType() {
        return type;
    }
    public void setType(String type) {
        if(type == null){
            throw new IllegalArgumentException("models.Space: Type is null.");
        }
        if(!type.equals("desk") && !type.equals("parking") && !type.equals("room") ){
            throw new IllegalArgumentException("models.Space: Type is incorrect - " + type);
        }
        this.type = type;
    }

    @Override
    public String toString() {
        return "Space{" +
                "spaceId=" + spaceId +
                ", name='" + name + '\'' +
                ", floor=" + floor +
                ", description='" + description + '\'' +
                ", isBlocked=" + isBlocked +
                ", type='" + type + '\'' +
                '}';
    }
}
