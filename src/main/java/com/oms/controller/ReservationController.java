package com.oms.controller;

import com.oms.DAO.IncomingReservationDAO;
import com.oms.DAO.OutgoingReservationDAO;
import com.oms.DAO.OutgoingReservationWithSpaceDAO;
import com.oms.exception.ResourceNotFoundException;
import com.oms.model.Reservation;
import com.oms.model.Space;
import com.oms.model.User;
import com.oms.repository.ReservationRepository;
import com.oms.repository.SpaceRepository;
import com.oms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/{spaceId}")
    public List<OutgoingReservationDAO> getReservationForSpace(@PathVariable Long spaceId){
        spaceRepository.findById(spaceId).orElseThrow(() -> new ResourceNotFoundException(
                "controller.ReservationController: Space with ID not exists: " + spaceId
        ));

        List<Reservation> foundReservations = reservationRepository.findReservationForSpace(spaceId);

        List<OutgoingReservationDAO> outgoingReservationDAOList = new ArrayList<>();
        foundReservations.forEach(reservation -> {
            outgoingReservationDAOList.add(new OutgoingReservationDAO(reservation.getStartDate(), reservation.getEndDate(), reservation.getSpace().getSpaceId()));
        });
        return outgoingReservationDAOList;
    }

    @GetMapping("/usersReservations/{userId}")
    public List<OutgoingReservationWithSpaceDAO> getUsersReservations(@PathVariable Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                "controller.ReservationController: User with ID not exists: " + userId
        ));

        List<Reservation> foundReservations = reservationRepository.findReservationsByUser(userId);

        List<OutgoingReservationWithSpaceDAO> outgoingReservationWithSpaceDAOList = new ArrayList<>();
        foundReservations.forEach(reservation -> {
            outgoingReservationWithSpaceDAOList.add(new OutgoingReservationWithSpaceDAO(reservation.getReservationId(), reservation.getStartDate(), reservation.getEndDate(), reservation.getSpace()));
        });

        outgoingReservationWithSpaceDAOList.sort(Comparator.comparing(OutgoingReservationWithSpaceDAO::getSpaceName));
        outgoingReservationWithSpaceDAOList.sort(Comparator.comparing(OutgoingReservationWithSpaceDAO::isSpaceBlocked));

        return outgoingReservationWithSpaceDAOList;
    }

    @PostMapping("")
    public ResponseEntity<Reservation> createReservation(@RequestBody IncomingReservationDAO incomingReservationDAO){
        Space foundSpace = spaceRepository.findById(incomingReservationDAO.getSpaceId()).orElseThrow(() -> new ResourceNotFoundException(
                "controller.ReservationController: Space with ID not exists: " + incomingReservationDAO.getSpaceId()
        ));
        User foundUser =  userRepository.findById(incomingReservationDAO.getUserId()).orElseThrow(() -> new ResourceNotFoundException(
                "controller.ReservationController: User with ID not exists: " + incomingReservationDAO.getUserId()
        ));

        Reservation reservation = new Reservation(
                incomingReservationDAO.getStartDate(),
                incomingReservationDAO.getEndDate(),
                foundSpace,
                foundUser
        );

        return ResponseEntity.ok(reservationRepository.save(reservation));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Map<String, Boolean>> deleteReservation(@PathVariable Long reservationId) {
        Reservation foundReservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ResourceNotFoundException(
                "controller.ReservationController: Reservation with ID not exists: " + reservationId
        ));

        reservationRepository.delete(foundReservation);
        Map<String, Boolean> response = new HashMap<>();

        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
