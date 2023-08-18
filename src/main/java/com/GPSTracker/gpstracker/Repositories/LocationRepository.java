package com.GPSTracker.gpstracker.Repositories;

import com.GPSTracker.gpstracker.Models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@EnableTransactionManagement
@EnableJpaRepositories
@Transactional
public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Query(value = "SELECT lon,lat  FROM location AS T1   WHERE EXISTS   (SELECT * FROM device as T2  WHERE T1.uid_loc = T2.uid  AND T2.uid = ?1 )  order by date_and_time DESC Limit 1", nativeQuery = true)
    public String getLonAndLatFromConnection(Integer uid);
    @Query(value = "SELECT *  FROM location AS T1   WHERE EXISTS   (SELECT * FROM device as T2  WHERE T1.uid_loc = T2.uid  AND T2.uid = ?1 )  order by date_and_time DESC Limit 20", nativeQuery = true)
    public List<Location> getListOfLonAndLatFromConnection(Integer uid);


}
