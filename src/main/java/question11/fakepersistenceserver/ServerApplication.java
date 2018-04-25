package question11.fakepersistenceserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@SpringBootApplication(scanBasePackages = "question11.fakepersistenceserver")
public class ServerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args) {
        System.setProperty("server.port", "9090");
        SpringApplication.run(ServerApplication.class, args);
    }

    @RestController
    public static class HelloWorldReactorRestController {

        @GetMapping(value = "/question11/fakepersistencevalues", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        Flux<Integer> fakepersistencevalues() {
            return Flux.range(1, 1_000_000)
                    .doOnNext(i -> LOGGER.info("value from fake persistency: {}", i));
        }
    }
}
