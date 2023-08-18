package com.GPSTracker.gpstracker.Repositories;

import com.GPSTracker.gpstracker.Models.HeartbeatStates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Repository
@EnableTransactionManagement
@EnableJpaRepositories
@Transactional
public interface HeartbeatStatesRepository extends JpaRepository<HeartbeatStates,Integer> {

    @Modifying
    @Query(value = "INSERT INTO heartbeat_states (uid, last_saved_time) VALUES (?1, ?2) ON DUPLICATE KEY UPDATE last_saved_time = VALUES(last_saved_time)", nativeQuery = true)
    void saveOrUpdate(Integer uid, Timestamp lastSavedTime);

    @Query(value = "SELECT last_saved_time FROM heartbeat_states where uid = ?1", nativeQuery = true)
    Timestamp getLastSavedTime(Integer uid);

}
