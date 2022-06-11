package com.oms.controller;

import com.oms.DAO.IncomingUserDAO;
import com.oms.DAO.OutgoingUserDAO;
import com.oms.exception.ResourceNotFoundException;
import com.oms.model.Reservation;
import com.oms.model.User;
import com.oms.repository.ReservationRepository;
import com.oms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping("")
    public List<OutgoingUserDAO> getUsers(){
        List<User> foundUsers = userRepository.findAll();

        List<OutgoingUserDAO> outgoingUserDAOList = new ArrayList<>();
        foundUsers.forEach(user -> {
            Long managerId = (user.getManager() != null ? user.getManager().getUserId() : null);
            outgoingUserDAOList.add(new OutgoingUserDAO(user.getUserId(), user.getFirstName(), user.getLastName(),
                    user.getEmail(), user.getOccupation(), managerId, user.getDateOfBirth(),
                    user.getUserRole()));
        });

        outgoingUserDAOList.sort(Comparator.comparing(OutgoingUserDAO::getFirstName));

        return outgoingUserDAOList;
    }

    @GetMapping("/findSubordinates/{managerId}")
    private List<OutgoingUserDAO> getUsersWithManagerId(@PathVariable Long managerId) {
        List<User> foundUsers = userRepository.findSubordinates(managerId);

        List<OutgoingUserDAO> outgoingUserDAOList = new ArrayList<>();
        foundUsers.forEach(user -> {
            outgoingUserDAOList.add(new OutgoingUserDAO(user.getUserId(), user.getFirstName(), user.getLastName(),
                    user.getEmail(), user.getOccupation(), managerId, user.getDateOfBirth(),
                    user.getUserRole()));
        });

        outgoingUserDAOList.sort(Comparator.comparing(OutgoingUserDAO::getFirstName));

        return outgoingUserDAOList;
    }

    @GetMapping("/without/{userId}")
    private List<OutgoingUserDAO> getUsersWithoutUserId(@PathVariable Long userId) {
        List<User> foundUsers = userRepository.findAll();

        List<OutgoingUserDAO> outgoingUserDAOList = new ArrayList<>();
        foundUsers.forEach(user -> {
            if(!Objects.equals(user.getUserId(), userId)) {
                Long managerId = (user.getManager() != null ? user.getManager().getUserId() : null);
                outgoingUserDAOList.add(new OutgoingUserDAO(user.getUserId(), user.getFirstName(), user.getLastName(),
                        user.getEmail(), user.getOccupation(), managerId, user.getDateOfBirth(),
                        user.getUserRole()));
            }
        });

        outgoingUserDAOList.sort(Comparator.comparing(OutgoingUserDAO::getFirstName));

        return outgoingUserDAOList;
    }

    private void validateManagerHierarchy(User user, Long managerId) throws IllegalArgumentException {
        List<User> subordinates = userRepository.findSubordinates(user.getUserId());

        for (User subordinate : subordinates) {
            if(Objects.equals(subordinate.getUserId(), managerId)){
                throw new IllegalArgumentException(
                        "controller.UserController: User chosen as manager is already lower in hierarchy: " + managerId
                );
            }
            validateManagerHierarchy(subordinate, managerId);
        }
    }

    @PostMapping("")
    private ResponseEntity<User> createUser(@RequestBody IncomingUserDAO userDAO) {
        User foundManager = userRepository.findById(userDAO.getManagerId()).orElseThrow(() -> new ResourceNotFoundException(
                "controller.UserController: User with ID not exisits: " + userDAO.getManagerId()
        ));

        User newUser = new User(
                userDAO.getFirstName(),
                userDAO.getLastName(),
                userDAO.getEmail(),
                userDAO.getOccupation(),
                foundManager,
                userDAO.getDateOfBirth(),
                userDAO.getUserRole()
        );
        userRepository.save(newUser);

        return ResponseEntity.ok(newUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable Long userId){
        User foundUser;
        try {
            foundUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                    "controller.UserController: User with ID not exists: " + userId
            ));
        } catch (ResourceNotFoundException ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Reservation> foundReservations = reservationRepository.findReservationsByUser(foundUser.getUserId());

        reservationRepository.deleteAll(foundReservations);
        userRepository.delete(foundUser);

        return ResponseEntity.ok(foundUser);
    }

    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody IncomingUserDAO userDAO) {
        User foundUser = userRepository.findById(userDAO.getUserId()).orElseThrow(() -> new ResourceNotFoundException(
                "controller.UserController: User with ID not exists: " + userDAO.getUserId()
        ));


        User foundManager = null;
        if(userDAO.getManagerId() != null) {
            try {
                foundManager = userRepository.findById(userDAO.getManagerId()).orElseThrow(() -> new ResourceNotFoundException(
                        "controller.UserController: User with ID not exists: " + userDAO.getManagerId()
                ));
            } catch (ResourceNotFoundException ex){
                System.out.println(ex);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            try {
                if (Objects.equals(foundManager.getUserId(), foundUser.getUserId())) {
                    throw new IllegalArgumentException(
                            "controller.UserController: Manager ID is incorrect: "
                                    + foundManager.getUserId()
                                    + " ; must be different than User ID: "
                                    + foundUser.getUserId()
                    );
                }
            } catch (IllegalArgumentException ex){
                System.out.println(ex);
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            try {
                validateManagerHierarchy(foundUser, foundManager.getUserId());
            } catch (IllegalArgumentException ex) {
                System.out.println(ex);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        foundUser.setUserRole(userDAO.getUserRole());
        foundUser.setEmail(userDAO.getEmail());
        foundUser.setDateOfBirth(userDAO.getDateOfBirth());
        foundUser.setFirstName(userDAO.getFirstName());
        foundUser.setLastName(userDAO.getLastName());
        foundUser.setOccupation(userDAO.getOccupation());
        foundUser.setManager(foundManager);

        userRepository.save(foundUser);

        return ResponseEntity.ok(foundUser);
    }
}
