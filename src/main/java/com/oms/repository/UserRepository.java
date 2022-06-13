package com.oms.repository;

import com.oms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.manager.userId = :managerId")
    List<User> findSubordinates(Long managerId);

    User findByEmail(String email);
}
