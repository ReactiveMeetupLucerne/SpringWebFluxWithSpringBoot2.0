package question11.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@SpringBootApplication(scanBasePackages = "question11.apigateway")
public class ServerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @RestController
    public static class HelloWorldReactorRestController {

        @GetMapping(value = "/question11/apigateway", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        Flux<Integer> apigateway() {
            return WebClient.create("http://localhost:9090")
                    .get()
                    .uri("/question11/fakepersistencevalues")
                    .retrieve()
                    .bodyToFlux(Integer.class)
                    .delayElements(Duration.ofMillis(1), Schedulers.elastic())
                    .doOnNext(value -> LOGGER.info("Value in API bridge: {}", value));
        }
    }
}
