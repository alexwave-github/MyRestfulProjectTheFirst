package com.alexwave;

import com.alexwave.restful.repositories.AuthorRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
@DirtiesContext
public abstract class AbstractTestClass {


    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTestClass.class);

    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.2"));

    static {
        POSTGRES_CONTAINER
                .withReuse(true)
                .withDatabaseName("testDb")
                .withUsername("test")
                .withPassword("test")
                .withLogConsumer(new Slf4jLogConsumer(LOGGER))
                .start();
    }

    @Autowired
    private ApplicationContext applicationContext;


    @Autowired
    private AuthorRepository authorRepository;

    @DynamicPropertySource
    static void registerPostgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.liquibase.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.liquibase.user", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.liquibase.password", POSTGRES_CONTAINER::getPassword);
        registry.add("spring.liquibase.driver-class-name", POSTGRES_CONTAINER::getDriverClassName);
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(applicationContext);
    }

    @AfterEach
    void afterAll() {
        authorRepository.deleteAll();
    }
}