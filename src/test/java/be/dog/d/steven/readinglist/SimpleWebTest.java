package be.dog.d.steven.readinglist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ReadingListApplication.class,
        webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT,
        value = "server.port=8080")
public class SimpleWebTest {

    // Simple example when not using SSL

    @Test
    public void pageNotFound() {

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            RestTemplate rest = new RestTemplate();
            rest.getForObject(
                    "http://localhost:8080/bla", String.class);
        });
    }
}
