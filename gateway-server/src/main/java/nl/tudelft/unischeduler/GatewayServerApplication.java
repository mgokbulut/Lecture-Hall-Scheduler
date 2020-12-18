package nl.tudelft.unischeduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class GatewayServerApplication {

//    @Bean
//    @LoadBalanced
//    public WebClient.Builder getWebClientBuilder() {
//        return WebClient.builder();
//    }


    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

}
