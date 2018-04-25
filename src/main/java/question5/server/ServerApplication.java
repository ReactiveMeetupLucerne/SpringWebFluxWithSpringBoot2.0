package question5.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@SpringBootApplication(scanBasePackages = "question5.server")
public class ServerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @RestController
    public static class HelloWorldReactorRestController {

        @GetMapping(value = "/question5/slowflightprice", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        Flux<Integer> flightprice() {
            return Flux.interval(Duration.ofSeconds(10)).take(1)
                    .doOnSubscribe(subscription -> LOGGER.info("price is requested"))
                    .flatMap(tick -> Flux.just(250))
                    .doOnCancel(() -> LOGGER.info("cancelled"))
                    .doOnNext(price -> LOGGER.info("published price: {}", price));
        }
    }
}
