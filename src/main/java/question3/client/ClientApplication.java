package question3.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages = "question3.client")
public class ClientApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientApplication.class);

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

            Mono<Integer> flightPriceMono = webClient
                    .get()
                    .uri("/question3/flightprice")
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .doOnNext(price -> LOGGER.info("got flight price: {}", price));
            Mono<Integer> hotelPriceMono = webClient
                    .get()
                    .uri("/question3/hotelprice")
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .doOnNext(price -> LOGGER.info("got hotel price: {}", price));
            Mono<Integer> carPriceMono = webClient
                    .get()
                    .uri("/question3/carprice")
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .doOnNext(price -> LOGGER.info("got car price: {}", price));

            Mono.zip(flightPriceMono, hotelPriceMono, carPriceMono)
                    .map(priceTriple -> priceTriple.getT1() + priceTriple.getT2() + priceTriple.getT3())
                    .subscribe(
                            totalPrice -> LOGGER.info("total price: {}", totalPrice),
                            Throwable::printStackTrace
                    );
        };
    }

}
