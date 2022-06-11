package com.oms.services;

import com.oms.model.Desk;

public class SpaceService {
    public void isNewDeskCorrect(Desk desk) {
        StringBuilder error = new StringBuilder("");
        if(desk.getName() == null || desk.getName().equals("")) {
            error.append("Name is null or empty.\n");
        }
        if(desk.getDescription() == null || desk.getDescription().equals("")) {
            error.append("Description is null or empty.\n");
        }
        if(error.length() != 0) {
            System.out.println("TEST");
            throw new IllegalArgumentException(error.toString());
        }
    }
}
