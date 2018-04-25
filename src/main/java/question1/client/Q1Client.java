package question1.client;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import question1.shared.FinalFieldFoobar;
import question1.shared.JavaBeanFoobar;

import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages = "question1.client")
public class Q1Client {

    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(Q1Client.class)
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
                    .uri("/javabean")
                    .retrieve()
                    .bodyToFlux(JavaBeanFoobar.class)
                    .subscribe(
                            System.out::println,
                            System.err::println,
                            () -> { /* no-op */ }
                    );

            webClient
                    .get()
                    .uri("/private-field")
                    .retrieve()
                    .bodyToFlux(FinalFieldFoobar.class)
                    .subscribe(
                            System.out::println,
                            System.err::println,
                            () -> { /* no-op */ }
                    );
        };
    }

}
