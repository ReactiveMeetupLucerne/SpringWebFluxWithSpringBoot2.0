package question9.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages = "question9.client")
public class ClientApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientApplication.class);

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(ClientApplication.class)
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
                    .uri("/question9/slowmillionvalues")
                    .retrieve()
                    .bodyToFlux(String.class)
                    .subscribe(
                            i -> LOGGER.info("Got: {}", i),
                            throwable -> LOGGER.error("exception!", throwable),
                            () -> LOGGER.info("Done")
                    );
        };
    }

}
