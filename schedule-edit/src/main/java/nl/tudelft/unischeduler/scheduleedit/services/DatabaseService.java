package nl.tudelft.unischeduler.scheduleedit.services;

import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class DatabaseService {

    @Autowired
    protected WebClient.Builder webClientBuilder;

    @PostConstruct
    public void setUp() {
        webClientBuilder.baseUrl("http://database-service/");
    }
}
