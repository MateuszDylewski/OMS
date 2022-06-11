package com.oms.controller;

import com.oms.exception.InvalidSpaceTypeException;
import com.oms.exception.ResourceNotFoundException;
import com.oms.model.*;
import com.oms.repository.*;
import com.oms.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/spaces")
@CrossOrigin(origins = "http://localhost:3000")
public class SpaceController {

    @Autowired
    private SpaceRepository spaceRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private DeskRepository deskRepository;

    @GetMapping("")
    public List<Space> getSpaces() {

        List<Space> foundSpaces = spaceRepository.findAll();
        foundSpaces.sort(Comparator.comparing(Space::getName));
        foundSpaces.sort(Comparator.comparing(Space::isBlocked));

        return foundSpaces;
    }

    @PutMapping("/changeBlockStatus/{spaceId}")
    public ResponseEntity<Space> updateBlockedStatus(@PathVariable Long spaceId) {
        Space foundSpace = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "controller.SpaceController: Space with ID not exists: " + spaceId
                ));

        foundSpace.setBlocked(!foundSpace.isBlocked());
        Space updatedSpace = spaceRepository.save(foundSpace);
        return ResponseEntity.ok(updatedSpace);
    }

    @PutMapping("/updateDesk")
    public ResponseEntity<Space> updateDesk(@RequestBody Desk updatedDesk) {
        Space foundSpace = spaceRepository.findById(updatedDesk.getSpaceId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "controller.SpaceController: Space with ID not exists: " + updatedDesk.getSpaceId()
                ));
        if(!foundSpace.getType().equals("desk")) {
            throw new InvalidSpaceTypeException("controller.SpaceController: \"desk\" type expected, found: " + foundSpace.getType());
        }
        Desk desk = (Desk) foundSpace;
        desk.setName(updatedDesk.getName());
        desk.setDescription(updatedDesk.getDescription());
        desk.setFloor(updatedDesk.getFloor());

        return ResponseEntity.ok(spaceRepository.save(desk));
    }

    @PutMapping("/updateRoom")
    public ResponseEntity<Space> updateRoom(@RequestBody Room updatedRoom) {
        Space foundSpace = spaceRepository.findById(updatedRoom.getSpaceId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "controller.SpaceController: Space with ID not exists: " + updatedRoom.getSpaceId()
                ));
        if(!foundSpace.getType().equals("room")) {
            throw new InvalidSpaceTypeException("controller.SpaceController: \"room\" type expected, found: " + foundSpace.getType());
        }
        Room room = (Room) foundSpace;
        room.setName(updatedRoom.getName());
        room.setDescription(updatedRoom.getDescription());
        room.setFloor(updatedRoom.getFloor());
        room.setAllowedAttendees(updatedRoom.getAllowedAttendees());
        room.setArea(updatedRoom.getArea());

        return ResponseEntity.ok(spaceRepository.save(room));
    }

    @PutMapping("/updateParking")
    public ResponseEntity<Space> updateParking(@RequestBody Parking updatedParking) {
        Space foundSpace = spaceRepository.findById(updatedParking.getSpaceId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "controller.SpaceController: Space with ID not exists: " + updatedParking.getSpaceId()
                ));
        if(!foundSpace.getType().equals("parking")) {
            throw new InvalidSpaceTypeException("controller.SpaceController: \"parking\" type expected, found: " + foundSpace.getType());
        }
        Parking parking = (Parking) foundSpace;
        parking.setName(updatedParking.getName());
        parking.setDescription(updatedParking.getDescription());
        parking.setFloor(updatedParking.getFloor());
        parking.setSize(updatedParking.getSize());

        return ResponseEntity.ok(spaceRepository.save(parking));
    }

    @DeleteMapping("/deleteSpace/{spaceId}/{type}")
    public ResponseEntity<Map<String, Boolean>> deleteSpace(@PathVariable Long spaceId, @PathVariable String type) {
        Space foundSpace = spaceRepository.findById(spaceId).orElseThrow(() -> new ResourceNotFoundException(
                "controller.SpaceController: Space with ID not exists: " + spaceId
        ));

        List<Reservation> foundReservations = reservationRepository.findReservationForSpace(foundSpace.getSpaceId());

        reservationRepository.deleteAll(foundReservations);

        switch (type) {
            case "desk" :
                Desk foundDesk = deskRepository.findById(spaceId).orElseThrow(() -> new ResourceNotFoundException(
                        "controller.SpaceController: Desk with ID not exists: " + spaceId
                ));
                deskRepository.delete(foundDesk);
                spaceRepository.delete(foundSpace);
                break;
            case "parking":
                Parking foundParking = parkingRepository.findById(spaceId).orElseThrow(() -> new ResourceNotFoundException(
                        "controller.SpaceController: Parking with ID not exists: " + spaceId
                ));
                parkingRepository.delete(foundParking);
                spaceRepository.delete(foundSpace);
                break;
            case "room":
                Room foundRoom = roomRepository.findById(spaceId).orElseThrow(() -> new ResourceNotFoundException(
                        "controller.SpaceController: Room with ID not exists: " + spaceId
                ));
                roomRepository.delete(foundRoom);
                spaceRepository.delete(foundSpace);
                break;
        }
        Map<String, Boolean> response = new HashMap<>();

        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createDesk")
    public Space createDesk(@RequestBody Desk desk){
        return deskRepository.save(desk);
    }

    @PostMapping("/createRoom")
    public Space createRoom(@RequestBody Room room){
        return roomRepository.save(room);
    }

    @PostMapping("/createParking")
    public Space createParking(@RequestBody Parking parking){
        return parkingRepository.save(parking);
    }
}
