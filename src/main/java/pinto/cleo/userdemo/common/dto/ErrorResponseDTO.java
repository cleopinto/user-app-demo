package pinto.cleo.userdemo.common.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cleo on 5/6/18.
 */
public class ErrorResponseDTO<T> {

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(T error) {
        errors.add(error);
    }

    public ErrorResponseDTO(List<T> errors) {
        this.errors = errors;
    }

    private List<T> errors = new ArrayList<>();

    public List<T> getErrors() {
        return errors;
    }

    public void setErrors(List<T> errors) {
        this.errors = errors;
    }
}
