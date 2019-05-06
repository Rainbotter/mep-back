package lu.mypost.mep.controller;

import lu.mypost.mep.exception.AlreadyExistsException;
import lu.mypost.mep.exception.CantBeModifiedException;
import lu.mypost.mep.exception.NotFoundException;
import lu.mypost.mep.model.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        ErrorResponse errorDetails = ErrorResponse.builder()
                .code("INVALID_PARAMETERS")
                .description("Some provided parameters are empty, null or invalid")
                .build();

        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler()
    public final ResponseEntity<ErrorResponse> handleMepNotFoundException(NotFoundException ex) {
        ErrorResponse errorDetails = ErrorResponse.builder()
                .code("NOT_FOUND")
                .description(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler()
    public final ResponseEntity<ErrorResponse> handleMepNameAlreadyExistsException(AlreadyExistsException ex) {
        ErrorResponse errorDetails = ErrorResponse.builder()
                .code("ALREADY_EXISTS")
                .description("This resource is already used")
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    public final ResponseEntity<ErrorResponse> handleCantBeModifiedException(CantBeModifiedException ex) {
        ErrorResponse errorDetails = ErrorResponse.builder()
                .code("CANT_BE_MODIFIED")
                .description(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
