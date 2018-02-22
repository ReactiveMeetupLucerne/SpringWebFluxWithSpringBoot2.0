package b_webflux_helloworld.server;

import b_webflux_helloworld.shared.TextDto;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication(scanBasePackages = "b_webflux_helloworld.server")
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @RestController
    public static class HelloWorldReactorRestController {

        @GetMapping(value = "/restcontroller/reactor/helloworld", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        Flux<String> helloworld() {
            return Flux.concat(
                    Flux.just("H", "e", "l", "l", "o"),
                    Flux.just(" "),
                    Flux.just("W", "o", "r", "l", "d"),
                    Flux.just("!")
            );
        }

        @GetMapping(value = "/restcontroller/reactor/helloworldslow", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        Flux<String> helloworldslow() {
            return Flux.zip(
                    Flux.concat(
                            Flux.just("H", "e", "l", "l", "o"),
                            Flux.just(" "),
                            Flux.just("W", "o", "r", "l", "d"),
                            Flux.just("!")
                    ),
                    Flux.interval(Duration.ofSeconds(1), reactor.core.scheduler.Schedulers.elastic()),
                    (s, ticker) -> s
            );
        }

        @GetMapping(value = "/restcontroller/reactor/helloworlddto")
        Mono<TextDto> helloworlddto() {
            return Mono.just(new TextDto("Hello World!"));
        }
    }

    @RestController
    public static class HelloWorldRxJava2RestController {

        @GetMapping(value = "/restcontroller/rxjava2/helloworld", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        Flowable<String> helloworld() {
            return Flowable.concat(
                    Flowable.just("H", "e", "l", "l", "o"),
                    Flowable.just(" "),
                    Flowable.just("W", "o", "r", "l", "d"),
                    Flowable.just("!")
            );
        }

        @GetMapping(value = "/restcontroller/rxjava2/helloworldslow", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        Flowable<String> helloworldslow() {
            return Flowable.zip(
                    Flowable.concat(
                            Flowable.just("H", "e", "l", "l", "o"),
                            Flowable.just(" "),
                            Flowable.just("W", "o", "r", "l", "d"),
                            Flowable.just("!")
                    ),
                    Flowable.interval(1, TimeUnit.SECONDS, Schedulers.io()),
                    (s, ticker) -> s
            );
        }

        @GetMapping(value = "/restcontroller/rxjava2/helloworlddto")
        Single<TextDto> helloworlddto() {
            return Single.just(new TextDto("Hello World!"));
        }
    }

    @Bean
    RouterFunction<ServerResponse> routes() {
        return nest(
                path("/routerfunction"),
                route(
                        GET("/helloworld"),
                        request -> ok()
                                .contentType(MediaType.TEXT_EVENT_STREAM)
                                .body(
                                        helloWorldCharacterFlux(),
                                        String.class
                                )
                ).andRoute(
                        GET("/helloworlddto"),
                        request -> ok()
                                .body(
                                        Mono.just(new TextDto("Hello World!")),
                                        TextDto.class
                                )
                )
        );
        // hint: this looks much better using Kotlin. See counterRouter() in https://todd.ginsberg.com/post/springboot2-reactive-kotlin/
    }

    private Flux<String> helloWorldCharacterFlux() {
        return Flux.zip(
                Flux.concat(
                        Flux.just("H", "e", "l", "l", "o"),
                        Flux.just(" "),
                        Flux.just("W", "o", "r", "l", "d"),
                        Flux.just("!")
                ),
                Flux.interval(Duration.ofSeconds(1), reactor.core.scheduler.Schedulers.elastic()),
                (s, ticker) -> s
        );
    }
}
