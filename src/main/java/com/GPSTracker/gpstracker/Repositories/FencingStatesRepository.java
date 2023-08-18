package com.GPSTracker.gpstracker.Repositories;

import com.GPSTracker.gpstracker.Models.FencingStates;
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
public interface FencingStatesRepository extends JpaRepository<FencingStates,Integer> {

    @Modifying
    @Query(value = "INSERT INTO fencing_states (uid, fencing_status, last_saved_time) VALUES (?1, ?2, ?3) " +
            "ON DUPLICATE KEY UPDATE fencing_status = VALUES(fencing_status), last_saved_time = VALUES(last_saved_time) ", nativeQuery = true)
    void saveOrUpdate(Integer uid, Boolean fencingStatus,Timestamp lastSavedTime);

    @Query(value = "SELECT fencing_status FROM fencing_states where uid = ?1", nativeQuery = true)
    Boolean getFencingStatus(Integer uid);

    @Query(value = "SELECT last_saved_time FROM fencing_states where uid = ?1", nativeQuery = true)
    Timestamp getLastSavedTime(Integer uid);

}
