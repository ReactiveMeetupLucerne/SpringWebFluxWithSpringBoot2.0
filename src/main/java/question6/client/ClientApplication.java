package question6.client;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(question6.client.ClientApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        TimeUnit.SECONDS.sleep(100); // keep the JVM alive
    }

    @Bean
    ApplicationRunner runner() {
        return args -> {
            WebClient webClient = WebClient.create("http://localhost:8080");

            webClient
                    .get()
                    .uri("/question6/millionvalues")
                    .retrieve()
                    .bodyToFlux(String.class)
                    .take(1_000_000)
                    .zipWith(
                            Flux.interval(Duration.ofMillis(100))
                    ).map(Tuple2::getT1)
                    .subscribe(
                            System.out::println,
                            Throwable::printStackTrace,
                            () -> System.out.println("Fertig")
                    );
        };
    }

}
