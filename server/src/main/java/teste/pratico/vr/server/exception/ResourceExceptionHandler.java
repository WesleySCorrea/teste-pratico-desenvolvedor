package teste.pratico.vr.server.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import teste.pratico.vr.server.exception.runtime.ObjectNotFoundException;
import teste.pratico.vr.server.exception.runtime.PersistFailedException;
import teste.pratico.vr.server.exception.runtime.ValidationNotPermissionException;

import java.time.Instant;
import java.util.Collections;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> objectNotFoundException(ObjectNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(e.getMessage());
        err.setPath(request.getRequestURI());
        err.setErrors(Collections.singletonList(new FieldMessage("Objeto não encontrado",e.getMessage())));
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(PersistFailedException.class)
    public ResponseEntity<Object> persistFailedException(PersistFailedException e,HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(e.getMessage());
        err.setPath(request.getRequestURI());
        err.setErrors(Collections.singletonList(new FieldMessage("Falha ao persistir o objeto",e.getMessage())));
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ValidationNotPermissionException.class)
    public ResponseEntity<Object> validationNotPermissionException(ValidationNotPermissionException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Validação não aceita");
        err.setPath(request.getRequestURI());
        err.setErrors(Collections.singletonList(new FieldMessage("Falha ao validar o objeto",e.getMessage())));
        return ResponseEntity.status(status).body(err);
    }
}
