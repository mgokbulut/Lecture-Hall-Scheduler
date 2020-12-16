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


@RestController
@EnableEurekaClient
@RequestMapping(path = "/demo")
public class ScheduleGenerateController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private RestTemplate restTemplate;

    /***
     * <p>This method makes an example request.</p>
     *
     * @return returns what the request returns.
     */
    @GetMapping(path = "/demo")
    public String returnReq() {
        //This method takes a Head with key Authorization which contains the authentication token
        //if this header is not present, it will return 400 bad request
        String res = restTemplate.getForObject("http://schedule-edit-service/makeareq", String.class);

        String res2 = webClientBuilder.build().get().uri("http://schedule-edit-service/makeareq").accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(String.class).block();
        System.out.println("res: " + res);
        System.out.println("res2: " + res2);
        return res2;
        //        try {
        //            String res = webClientBuilder.build()
        //                    .get()
        //                    .uri("http://schedule-edit-service/makeareq") //https://
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

    /***
     * <p>This is a test authorization method.</p>
     *
     * @param token is the user credential token
     * @return returns message
     */
    @GetMapping(path = "/test")
    public String returntest(@RequestHeader(name = "Authorization") String token) {
        //This method takes a Head with key Authorization which contains the authentication token
        //if this header is not present, it will return 400 bad request
        try {
            String res = webClientBuilder.build()
                    .get()
                    .uri("localhost:8081/test")
                    .header("Authorization", token)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return res;
        } catch (Exception e) {

            System.out.println("Can't reach the desired address - webclient failed");
            return "null";
        }
    }

    public WebClient.Builder getWebClientBuilder() {
        return webClientBuilder;
    }

    public void setWebClientBuilder(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}