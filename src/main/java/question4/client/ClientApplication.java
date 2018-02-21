package question4.client;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(ClientApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        TimeUnit.SECONDS.sleep(10); // keep the JVM alive
    }

    @Bean
    ApplicationRunner runner() {
        return args -> {
            WebClient webClient = WebClient.create("http://localhost:8080");

            webClient
                    .get()
                    .uri("/question4/millionvalues")
                    .retrieve()
                    .bodyToFlux(String.class)
                    .take(1000)
                    .subscribe(
                            System.out::println,
                            Throwable::printStackTrace,
                            () -> System.out.println("Fertig")
                    );
        };
    }

}
