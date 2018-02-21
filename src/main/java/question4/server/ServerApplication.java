package question4.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@SpringBootApplication(scanBasePackages = "question4.server")
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @RestController
    public static class HelloWorldReactorRestController {

        @GetMapping(value = "/question4/millionvalues", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        Flux<Integer> millionvalues() {
            return Flux.range(1, 1_000_000)
                    .doOnNext(System.out::println);
        }
    }
}
