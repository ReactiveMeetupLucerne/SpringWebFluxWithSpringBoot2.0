package question3.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootApplication(scanBasePackages = "question3.server")
public class ServerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @RestController
    public static class HelloWorldReactorRestController {

        @GetMapping(value = "/question3/flightprice")
        Mono<Integer> flightprice() {
            return Mono.just(250)
                    .delayElement(Duration.ofSeconds(1))
                    .doOnNext(price -> LOGGER.info("flight: {}", price));
        }

        @GetMapping(value = "/question3/hotelprice")
        Mono<Integer> hotelprice() {
            return Mono.just(150)
                    .delayElement(Duration.ofSeconds(1))
                    .doOnNext(price -> LOGGER.info("hotel: {}", price));
        }

        @GetMapping(value = "/question3/carprice")
        Mono<Integer> carprice() {
            return Mono.just(100)
                    .delayElement(Duration.ofSeconds(1))
                    .doOnNext(price -> LOGGER.info("car: {}", price));
        }
    }
}
