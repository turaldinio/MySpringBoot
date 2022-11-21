package ru.guluev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MySpringBootApplicationTests {

    public static GenericContainer<?> firstContainer = new GenericContainer<>("devapp").withExposedPorts(8080);
    public static GenericContainer<?> secondContainer = new GenericContainer<>("prodapp").withExposedPorts(8080);

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeAll
    public static void setUp() {
        firstContainer.start();
        secondContainer.start();
    }

    @Test
    void firstImage() {
        int first = firstContainer.getMappedPort(8080);

        ResponseEntity<?> responseEntity = restTemplate.getForEntity("http://localhost:" + first + "/profile", String.class);
        Assertions.assertEquals("Current profile is dev", responseEntity.getBody());
    }

    @Test
    void secondImage() {
        int second = secondContainer.getMappedPort(8080);

        ResponseEntity<?> responseEntity = restTemplate.getForEntity("http://localhost:" + second + "/profile", String.class);
        Assertions.assertEquals("Current profile is production", responseEntity.getBody());
    }

}
