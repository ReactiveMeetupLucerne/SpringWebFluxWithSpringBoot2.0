package question1.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import question1.JavaBeanFoobar;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class Q1Server {

	public static void main(String[] args) {
		SpringApplication.run(Q1Server.class, args);
	}

	@RestController
	public static class Question1Controller {

		/*
		 */

		/**
		 * Expected:
		 * No serializer found for class question1.FoobarNotSerializable and no properties discovered to create
		 * BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)
		 *//*

        @GetMapping(value = "/private-field")
        Mono<PrivateFieldFoobar> privateField() {
            return Mono.just(new PrivateFieldFoobar());
        }
*/
		@GetMapping(value = "/javabean")
		Mono<JavaBeanFoobar> javabean() {
			return Mono.just(new JavaBeanFoobar());
		}
	}

}
