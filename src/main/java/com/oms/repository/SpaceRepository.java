package com.oms.repository;

import com.oms.model.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    @Query("SELECT s FROM Space s WHERE s.blocked = false")
    List<Space> findByBlockedStatus(boolean blocked);

}
