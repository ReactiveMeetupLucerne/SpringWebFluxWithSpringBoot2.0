package question1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication(scanBasePackages = "question1")
public class Q1Server {
    public static void main(String[] args) {
        SpringApplication.run(Q1Server.class, args);
    }

    @RestController
    public static class Question1Controller {

        @GetMapping(value = "/not-serializable")
        Mono<FoobarNotSerializable> notserializable() {
            return Mono.just(new FoobarNotSerializable());
        }
    }

}
