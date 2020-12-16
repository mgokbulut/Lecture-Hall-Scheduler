package nl.tudelft.unischeduler.scheduleedit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class ConnectionController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping(path = "/makeareq")
    public String returnReq() {
        return "this is a response from schedule edit";
    }
}
