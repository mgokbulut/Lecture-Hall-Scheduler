package nl.tudelft.unischeduler.schedulegenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;


@RestController
@EnableEurekaClient
@RequestMapping(path="/demo")
public class ScheduleGenerateController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(path = "/demo")
    public String returnReq() {
        //This method takes a Head with key Authorization which contains the authentication token
        //if this header is not present, it will return 400 bad request
        String res = restTemplate.getForObject("http://schedule-edit-service/makeareq", String.class);

        String res2 = webClientBuilder.build()
                .get()
                .uri("http://schedule-edit-service/makeareq") //https://
//                .accept(MediaType.ALL)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("res: " + res);
        System.out.println("res2: " + res2);
        return res2;
////        try {
//            String res = webClientBuilder.build()
//                    .get()
//                    .uri("http://schedule-edit-service/makeareq") //https://
////                .accept(MediaType.ALL)
//                    .accept(MediaType.APPLICATION_JSON)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .block();
//            return res;
//        }k
//        catch(Exception e) {
//            System.out.println("Can't reach the desired address - webclient failed");
//            return "null";
//        }
    }

    @GetMapping(path = "/test")
    public String returntest(@RequestHeader(name = "Authorization") String token) {
        //This method takes a Head with key Authorization which contains the authentication token
        //if this header is not present, it will return 400 bad request
        try {
            String res = webClientBuilder.build()
                    .get()
                    .uri("localhost:8081/test")
                    .header("Authorization", token)
//                .accept(MediaType.ALL)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return res;
        }
        catch(Exception e) {

            System.out.println("Can't reach the desired address - webclient failed");
            return "null";
        }
        //System.out.println(res);

        //String res = webClientBuilder.baseUrl("https://localhost:8081").build().get().uri("/test").header("Authorization", token).retrieve().bodyToMono(String.class).block();
//        if(res != null) {
//            System.out.println(res);
//        }
        //res.subscribe(s -> System.out.println("I need to see " + s));
//        res.subscribe(value -> {
//            x.set(value);
//        });
//        System.out.println(x.get());

       // return "this is the info that you are looking for";
    }
}