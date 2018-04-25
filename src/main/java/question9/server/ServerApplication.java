package question9.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@SpringBootApplication(scanBasePackages = "question9.server")
public class ServerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @RestController
    public static class HelloWorldReactorRestController {

        @GetMapping(value = "/question9/slowmillionvalues", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        Flux<Integer> millionvalues() {
            return Flux.range(1, 1_000_000)
                    .delayElements(Duration.ofSeconds(1), Schedulers.elastic())
                    .doOnNext(i -> LOGGER.info("Produced: {}", i));
        }
    }
}
