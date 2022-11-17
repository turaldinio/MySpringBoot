package ru.guluev;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MySpringBootApplicationTests {

    public static GenericContainer<?> firstContainer = new GenericContainer<>("devapp");
    public static GenericContainer<?> secondContainer = new GenericContainer<>("prodapp");

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
        System.out.println(responseEntity.getBody());
    }

    @Test
    void secondImage() {
        int second = secondContainer.getMappedPort(8080);

        ResponseEntity<?> responseEntity = restTemplate.getForEntity("http://localhost:" + second + "/profile", String.class);

        System.out.println(responseEntity.getBody());
    }

}
