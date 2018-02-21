package b_webflux_helloworld;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages = "b_webflux_helloworld")
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @RestController
    public static class HelloWorldReactorRestController {

        @GetMapping(value = "/reactor/restcontroller/helloworld", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        Flux<String> helloworld() {
            return Flux.concat(
                    Flux.just("H", "e", "l", "l", "o"),
                    Flux.just(" "),
                    Flux.just("W", "o", "r", "l", "d"),
                    Flux.just("!")
            );
        }

        @GetMapping(value = "/reactor/restcontroller/helloworldslow", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
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

        @GetMapping(value = "/reactor/restcontroller/helloworlddto")
        Mono<TextDto> helloworlddto() {
            return Mono.just(new TextDto("Hello World!"));
        }
    }

    @RestController
    public static class HelloWorldRxJava2RestController {

        @GetMapping(value = "/rxjava2/restcontroller/helloworld", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        Flowable<String> helloworld() {
            return Flowable.concat(
                    Flowable.just("H", "e", "l", "l", "o"),
                    Flowable.just(" "),
                    Flowable.just("W", "o", "r", "l", "d"),
                    Flowable.just("!")
            );
        }

        @GetMapping(value = "/rxjava2/restcontroller/helloworldslow", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
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

        @GetMapping(value = "/rxjava2/restcontroller/helloworlddto")
        Single<TextDto> helloworlddto() {
            return Single.just(new TextDto("Hello World!"));
        }
    }
}
