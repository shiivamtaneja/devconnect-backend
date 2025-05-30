package com.shivamtaneja.devconnect.advice;

import java.io.IOException;
import java.util.stream.Collectors;

import com.shivamtaneja.devconnect.utils.HttpMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shivamtaneja.devconnect.common.response.ApiResponse;
import com.shivamtaneja.devconnect.exceptions.ExistsException;
import com.shivamtaneja.devconnect.exceptions.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
  /**
   * Handles validation exceptions when the request body fails @Valid checks.
   *
   * @param e the MethodArgumentNotValidException that was thrown. Contains
   *          information about the validation errors in the request.
   * @return a ResponseEntity containing an ApiResponse with HTTP status 400
   * (Bad Request), along with a detailed error message.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<String>> handleValidationException(MethodArgumentNotValidException e) {
    String errMessage = e.getBindingResult().getFieldErrors().stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.joining(", "));

    ApiResponse<String> response = new ApiResponse<>(
            HttpStatus.BAD_REQUEST.value(),
            HttpMessages.VALIDATION_ERROR,
            errMessage,
            false);

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles validation exceptions when the request body is missing or malformed.
   *
   * @return a ResponseEntity containing an ApiResponse with HTTP status 400
   * (Bad Request), along with a detailed error message.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<String>> handleMissingBody() {
    ApiResponse<String> response = new ApiResponse<>(
            HttpStatus.BAD_REQUEST.value(),
            HttpMessages.VALIDATION_ERROR,
            HttpMessages.REQUEST_BODY_MISSING,
            false);

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles Exists exceptions when creating new entity.
   *
   * @return a ExistsException containing an ApiResponse with HTTP status 409
   * (Conflict), along with a detailed error message.
   */
  @ExceptionHandler(ExistsException.class)
  public ResponseEntity<ApiResponse<String>> handleExistsError(ExistsException e) {
    ApiResponse<String> response = new ApiResponse<>(
            HttpStatus.CONFLICT.value(),
            e.getMessage(),
            HttpMessages.EXISTS_ERROR,
            false);

    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  /**
   * Handles Not Found exceptions.
   *
   * @return a NotFoundException containing an ApiResponse with HTTP status 404
   * (Not Found), along with a detailed error message.
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiResponse<String>> handleNotFoundException(NotFoundException e) {
    ApiResponse<String> response = new ApiResponse<>(
            HttpStatus.NOT_FOUND.value(),
            e.getMessage(),
            HttpMessages.NOT_FOUND_ERROR,
            false);

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles IOException.
   *
   * @param e the exception that was thrown.
   * @return a ResponseEntity containing an ApiResponse with HTTP status 500
   * (Internal Server Error), along with a detailed error message.
   */
  @ExceptionHandler(IOException.class)
  public ResponseEntity<ApiResponse<String>> handleIOException(IOException e) {
    ApiResponse<String> response = new ApiResponse<>(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpMessages.IO_ERROR,
            e.getMessage(),
            false);

    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handles general exceptions.
   *
   * @param e the exception that was thrown.
   * @return a ResponseEntity containing an ApiResponse with HTTP status 500
   * (Internal Server Error), along with a detailed error message.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
    log.error("Unhandled exception occured: ", e);
    ApiResponse<String> response = new ApiResponse<>(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpMessages.GENERAL_ERROR,
            e.getMessage(),
            false);

    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
