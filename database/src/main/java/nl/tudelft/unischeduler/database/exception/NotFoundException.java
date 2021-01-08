package nl.tudelft.unischeduler.database.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends IOException {
    private static final long serialVersionUID = 2L;

    public NotFoundException() {
        super();
    }
}
