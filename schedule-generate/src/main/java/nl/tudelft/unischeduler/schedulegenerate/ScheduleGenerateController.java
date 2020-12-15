package nl.tudelft.unischeduler.schedulegenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;


@RestController
@EnableEurekaClient
@RequestMapping(path="/demo")
public class ScheduleGenerateController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping(path = "/test")
    public String returntest(@RequestHeader(name = "Authorization") String token) {
        //This method takes a Head with key Authorization which contains the authentication token
        //if this header is not present, it will return 400 bad request
//        String res = webClientBuilder.build()
//                .get()
//                .uri("https://localhost:8081/examplepath")
//                .header("Authorization", token)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();

        //String res = webClientBuilder.baseUrl("https://localhost:8081").build().get().uri("/test").header("Authorization", token).retrieve().bodyToMono(String.class).block();
//        if(res != null) {
//            System.out.println(res);
//        }
        //res.subscribe(s -> System.out.println("I need to see " + s));
//        res.subscribe(value -> {
//            x.set(value);
//        });
//        System.out.println(x.get());

        return "this is the info that you are looking for";
    }
}