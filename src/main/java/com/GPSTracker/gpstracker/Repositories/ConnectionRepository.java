package com.GPSTracker.gpstracker.Repositories;

import com.GPSTracker.gpstracker.Models.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Repository
@EnableTransactionManagement
@EnableJpaRepositories
@Transactional
public interface ConnectionRepository extends JpaRepository<Connection, Integer> {


}