package com.oms.repository;

import com.oms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    //SELECT * FROM user WHERE email = {email};

    @Query("SELECT u FROM User u WHERE u.manager.userId = :managerId")
    List<User> findSubordinates(Long managerId);

    List<User> findByManager(User manager);
}
