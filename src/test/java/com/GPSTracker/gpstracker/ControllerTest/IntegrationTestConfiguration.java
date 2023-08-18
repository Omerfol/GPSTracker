package com.GPSTracker.gpstracker.ControllerTest;



import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@TestConfiguration
@Configuration
@Testcontainers
@Slf4j
@EnableTransactionManagement
public class IntegrationTestConfiguration {

    @ServiceConnection
    public static MySQLContainer mySQLContainer = new MySQLContainer<>(
            DockerImageName.parse("mysql:latest")
    ).withUsername("piotr")
            .withPassword("zaq1@WSX")
            .withDatabaseName("testGPSdb");

    @Bean
    @DynamicPropertySource
    @ServiceConnection
    @BeforeAll
    static MySQLContainer properties(DynamicPropertyRegistry registry) {
        mySQLContainer.start();
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);

        return mySQLContainer;
    }
}
