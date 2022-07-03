package account.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FieldValidationException extends RuntimeException {
    public FieldValidationException(String cause) {
        super(cause);
    }
}
