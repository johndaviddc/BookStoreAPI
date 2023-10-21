package dave.dev.bookstore.exception;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", details);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ConfigDataResourceNotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, "Resource Not Found", details);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> details = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> details.add(error.getDefaultMessage()));
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Validation Error", details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
