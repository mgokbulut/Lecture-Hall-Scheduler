package nl.tudelft.unischeduler.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.beans.BeanProperty;

@SpringBootApplication
@EnableEurekaClient
public class AuthenticationApplication {


	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder getWebClientBuilder() {
		 return WebClient.builder();
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}

}
