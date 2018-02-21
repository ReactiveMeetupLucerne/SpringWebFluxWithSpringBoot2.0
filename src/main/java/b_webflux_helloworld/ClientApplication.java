package b_webflux_helloworld;

import io.reactivex.Single;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ClientApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    ApplicationRunner runner() {
        return args -> {
            WebClient webClient = WebClient.create("http://localhost:8080");

            webClient
                    .get()
                    .uri("/reactor/restcontroller/helloworld")
                    .retrieve()
                    .bodyToFlux(String.class)
                    .subscribe(System.out::println);

            webClient
                    .get()
                    .uri("/reactor/restcontroller/helloworlddto")
                    .retrieve()
                    .bodyToMono(TextDto.class)
                    .subscribe(System.out::println);

            Single.timer(10, TimeUnit.SECONDS)
                    .subscribe(ticker -> System.exit(0));
        };
    }

}
