package nl.tudelft.unischeduler.scheduleedit.services;

import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.unischeduler.scheduleedit.exception.ConnectionException;
import nl.tudelft.unischeduler.scheduleedit.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class DatabaseService {

    @Autowired
    protected WebClient.Builder webClientBuilder;

    protected WebClient webClient;

    @PostConstruct
    public void setUp() {
        webClientBuilder.baseUrl("http://database-service/");
        webClient = webClientBuilder.build();
    }

    /**
     * This method checks wheter the status code of the response is Ok
     * and if it is not will throw the appropriate exception.
     *
     * @param response the response object gotten from the webclient.
     * @throws IOException When response is null or the status code != 200.
     */
    public void verifyStatusCode(ResponseEntity<Void> response) throws IOException {
        if (response == null) {
            throw new ConnectionException("the response was null");
        }
        HttpStatus status = response.getStatusCode();
        switch (status) {
            case OK:
                return;
            case NOT_FOUND:
                throw new NotFoundException();
            default:
                throw new ConnectionException("The connection with the database failed");
        }
    }
}
