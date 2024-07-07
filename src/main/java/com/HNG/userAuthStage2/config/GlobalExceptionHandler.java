package com.HNG.userAuthStage2.config;



import com.HNG.userAuthStage2.domain.dtos.FieldValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.MethodArgumentNotValidException;
        import org.springframework.web.bind.annotation.ControllerAdvice;
        import org.springframework.web.bind.annotation.ExceptionHandler;
        import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.List;
        import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FieldValidationError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldValidationError.FieldErrorDto> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> FieldValidationError.FieldErrorDto.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        FieldValidationError errorResponse = FieldValidationError.builder()
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

