package question1;

import io.reactivex.Single;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Q1Client {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Q1Client.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    ApplicationRunner runner() {
        return args -> {
            WebClient webClient = WebClient.create("http://localhost:8080");

            webClient
                    .get()
                    .uri("/not-serializable")
                    .retrieve()
                    .bodyToFlux(FoobarNotSerializable.class)
                    .subscribe(System.out::println);

            Single.timer(10, TimeUnit.SECONDS)
                    .subscribe(ticker -> System.exit(0));
        };
    }

}
