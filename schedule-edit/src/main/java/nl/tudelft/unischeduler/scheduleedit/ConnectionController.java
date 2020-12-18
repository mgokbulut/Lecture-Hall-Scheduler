package nl.tudelft.unischeduler.scheduleedit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@Data
@AllArgsConstructor
public class ConnectionController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping(path = "/makeareq")
    public String returnReq() {
        return "this is a response from schedule edit";
    }

    @GetMapping(path = "/admindemo")
    public String admindemo() {
        return "admin demo yo";
    }

    @GetMapping(path = "/teacherdemo")
    public String teacherdemo() {
        return "teacher demo yo";
    }

    @GetMapping(path = "/authenticationdemo")
    public String authenticationdemo() {
        return "authentication demo yo";
    }
}
