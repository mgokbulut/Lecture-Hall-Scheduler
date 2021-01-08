package nl.tudelft.unischeduler.database.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IllegalDateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IllegalDateException(String message) {
        super(message);
    }

    public IllegalDateException(String message, Exception source) {
        super(message, source);
    }
}
