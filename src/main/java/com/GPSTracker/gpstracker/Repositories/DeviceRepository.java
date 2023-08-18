package com.GPSTracker.gpstracker.Repositories;

import com.GPSTracker.gpstracker.Models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Repository
@EnableTransactionManagement
@EnableJpaRepositories
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    @Query(value = "Select name from device where uid = ?1", nativeQuery = true)
    public String getNameByUid(Integer uid);

}
