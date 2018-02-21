package b_webflux_helloworld;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ClientApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    ApplicationRunner runner() {
        return args -> WebClient
                .create("http://localhost:8080")
                .get()
                .uri("/reactor/restcontroller/helloworld")
                .retrieve()
                .bodyToFlux(String.class)
                .subscribe(System.out::println);
    }

}
