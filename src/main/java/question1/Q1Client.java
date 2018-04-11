package question1;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Q1Client {

    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(Q1Client.class)
                .web(WebApplicationType.NONE)
                .run(args);

        TimeUnit.SECONDS.sleep(100); // keep the JVM alive
    }

    @Bean
    ApplicationRunner runner() {
        return args -> {
            WebClient webClient = WebClient.create("http://localhost:8080");

            // Just works:
            webClient
                    .get()
                    .uri("/javabean")
                    .retrieve()
                    .bodyToFlux(JavaBeanFoobar.class)
                    .subscribe(System.out::println,
                            System.err::println);

            // Throws exception on the server side:

            webClient
                    .get()
                    .uri("/private-field")
                    .retrieve()
                    .bodyToFlux(PrivateFieldFoobar.class)
                    .subscribe(System.out::println,
                            System.err::println);
        };
    }

}
