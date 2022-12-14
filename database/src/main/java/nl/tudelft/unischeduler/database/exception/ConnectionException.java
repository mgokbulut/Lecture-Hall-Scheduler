package nl.tudelft.unischeduler.database.exception;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class ConnectionException extends IOException {
    private static final long serialVersionUID = 2L;

    public ConnectionException(String message) {
        super(message);
    }
}
