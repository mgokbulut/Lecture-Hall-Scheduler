package nl.tudelft.unischeduler.viewer.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Data
@Service
public class ReturnList {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<Object[]> returnlist(String uri, String id){
        return webClientBuilder.build()
                .get()
                .uri(uri + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Object[].class)
                .collectList()
                .block();
    }
}
