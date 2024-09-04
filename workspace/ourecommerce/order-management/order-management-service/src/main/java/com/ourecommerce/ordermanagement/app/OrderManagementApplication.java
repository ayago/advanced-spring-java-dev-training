package com.ourecommerce.ordermanagement.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class OrderManagementApplication {

	public static void main(String[] args) throws InterruptedException{
		SpringApplication.run(OrderManagementApplication.class, args);
		
		for (int i = 0; i < 3; i++) {
			getString()
				.map(s -> {
					System.out.println(Instant.now() + " Thread:" + Thread.currentThread().getName() + " " + s);
					return s.length();
				})
				.subscribeOn(Schedulers.parallel())
				.subscribe();
			System.out.println(Instant.now() + " Loop: " + i + " " + "after MONO");
		}
		
		TimeUnit.SECONDS.sleep(1);
	}
	
	public static Mono<String> getString() {
		return Mono.delay(Duration.ofSeconds(1))
			.map(it -> "hello world");
	}

}
