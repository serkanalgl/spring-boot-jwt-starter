package com.serkanalgl.jwt.starter.common.exception;

import com.serkanalgl.jwt.starter.common.model.dto.ApiErrorDTO;
import com.serkanalgl.jwt.starter.common.model.FieldError;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler(BadRequestException.class)
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<ApiErrorDTO> handleBadRequest(final BadRequestException exception) {
        ApiErrorDTO apiErrorDTO = ApiErrorDTO.builder()
                .code(exception.getCode())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(apiErrorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ApiResponse(responseCode = "403", description = "Access denied")
    public ResponseEntity<ApiErrorDTO> handleAccessDenied(final AccessDeniedException exception) {
        ApiErrorDTO apiErrorDTO = ApiErrorDTO.builder()
                .code("ACCESS_DENIED")
                .message("Access denied. You are not authorized to perform this operation.")
                .build();
        return new ResponseEntity<>(apiErrorDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Not found")
    public ResponseEntity<ApiErrorDTO> handleNotFound(final NotFoundException exception) {
        ApiErrorDTO apiErrorDTO = ApiErrorDTO.builder()
                .code(exception.getCode())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(apiErrorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception) {

        final BindingResult bindingResult = exception.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .map(error -> {
                    final FieldError fieldFieldError = new FieldError();
                    fieldFieldError.setField(error.getField());
                    fieldFieldError.setCode(error.getCode());
                    fieldFieldError.setMessage(error.getDefaultMessage());
                    return fieldFieldError;
                })
                .collect(Collectors.toList());

        ApiErrorDTO apiErrorDTO = ApiErrorDTO.builder()
                .code("INVALID_PARAMETERS")
                .message("Invalid request parameters")
                .fieldErrors(fieldErrors)
                .build();

        return new ResponseEntity<>(apiErrorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ApiErrorDTO> handleMediaType(final HttpMediaTypeNotAcceptableException exception) {

        ApiErrorDTO apiErrorDTO = ApiErrorDTO.builder()
                .code("INVALID_MEDIA_TYPE")
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(apiErrorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(responseCode = "500", description = "Internal error")
    public ResponseEntity<ApiErrorDTO> handleException(final Throwable exception) {
        log.error("unexpected error", exception);
        ApiErrorDTO apiErrorDTO = ApiErrorDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .build();
        return new ResponseEntity<>(apiErrorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}