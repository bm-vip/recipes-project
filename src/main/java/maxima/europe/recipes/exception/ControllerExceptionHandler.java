package maxima.europe.recipes.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception) {
        String details = exception.getConstraintViolations().stream()
                .map(m -> String.format("{%s %s %s}", m.getRootBeanClass().getName(), m.getPropertyPath(), m.getMessage()))
                .collect(Collectors.joining(", "));
//        var apiError = new ApiErrorModel(request.getDescription(false), BAD_REQUEST, exception.getMessage(), errors);
        log.error(details);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
