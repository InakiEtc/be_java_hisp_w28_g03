package com.mercadolibre.socialmeli_g3.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mercadolibre.socialmeli_g3.dto.response.ExceptionDTO;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFound(NotFoundException e){
        ExceptionDTO exceptionDto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(BadRequestException e){
        ExceptionDTO exceptionDto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> invalidOperation(ConflictException e){
        ExceptionDTO exceptionDto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ExceptionDTO exceptionDto = new ExceptionDTO("Data request invalid", errors.toString());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDTO> handleValidationExceptions(HttpMessageNotReadableException e) {
        String errorMessage = "Tipo de dato inválido en la solicitud.";
        Throwable mostSpecificCause = e.getMostSpecificCause();

        if (mostSpecificCause != null) {
            String exceptionMessage = mostSpecificCause.getMessage();

            try {
                String expectedType = null;
                String fieldName = null;

                if (exceptionMessage.contains("Cannot deserialize value of type")) {
                    String[] parts = exceptionMessage.split("`");
                    if (parts.length > 1) {
                        expectedType = parts[1]; // ej. `java.lang.Integer`
                    }

                    int fieldStart = exceptionMessage.indexOf("through reference chain: ");
                    if (fieldStart != -1) {
                        String fieldPath = exceptionMessage.substring(fieldStart);
                        String[] fieldParts = fieldPath.split("\\[");
                        if (fieldParts.length > 1) {
                            String rawFieldName = fieldParts[fieldParts.length - 1];
                            fieldName = rawFieldName.replace("]", "").replace("\"", "").replace(")", "").trim();
                        }
                    }

                    if (fieldName != null && fieldName.endsWith(")")) {
                        fieldName = fieldName.substring(0, fieldName.length() - 1);
                    }
                }

                if (expectedType != null && fieldName != null) {
                    errorMessage = String.format("%s: Expect %s", fieldName, expectedType.substring(expectedType.lastIndexOf('.') + 1));
                }
            } catch (Exception parseException) {
                // Ignora el error de parseo
            }
        }

        ExceptionDTO error = new ExceptionDTO("Invalid data type", errorMessage);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
