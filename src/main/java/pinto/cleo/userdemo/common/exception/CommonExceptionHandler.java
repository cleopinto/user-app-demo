package pinto.cleo.userdemo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pinto.cleo.userdemo.common.dto.ErrorResponseDTO;
import pinto.cleo.userdemo.common.dto.FieldErrorDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cleo on 5/5/18.
 */
@ControllerAdvice
public class CommonExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ErrorResponseDTO handleResourceNotFound(ResourceNotFoundException e) {
        ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ErrorResponseDTO handleJsonParseException(Exception e) {
        ErrorResponseDTO response = new ErrorResponseDTO(String.format("There was an error parsing the payload."));
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ErrorResponseDTO handleBadRequest(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<FieldErrorDTO> mappedErrors = fieldErrors.stream()
                .map(error ->
                    new FieldErrorDTO(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        ErrorResponseDTO response = new ErrorResponseDTO(mappedErrors);
        return response;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponseDTO handleGenericException(Exception e) {
        ErrorResponseDTO response = new ErrorResponseDTO(String.format("There was an unexpected error."));
        return response;
    }
}
