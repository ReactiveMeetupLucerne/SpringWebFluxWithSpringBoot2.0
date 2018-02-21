package b_webflux_helloworld.client;

import b_webflux_helloworld.shared.TextDto;
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
                    .uri("/restcontroller/reactor/helloworld")
                    .retrieve()
                    .bodyToFlux(String.class)
                    .subscribe(
                            System.out::println,
                            Throwable::printStackTrace,
                            () -> System.out.println("Fertig")
                    );

            webClient
                    .get()
                    .uri("/restcontroller/reactor/helloworlddto")
                    .retrieve()
                    .bodyToMono(TextDto.class)
                    .subscribe(
                            System.out::println,
                            Throwable::printStackTrace,
                            () -> System.out.println("Fertig")
                    );
        };
    }

}
