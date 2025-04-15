package com.shivamtaneja.devconnect.advice;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shivamtaneja.devconnect.common.response.ApiResponse;
import com.shivamtaneja.devconnect.utils.StringConstants;

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
   *         (Bad Request), along with a detailed error message.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException e) {
    String errMessage = e.getBindingResult().getFieldErrors().stream()
        .map(err -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.joining(", "));

    ApiResponse response = new ApiResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Validation failed",
        errMessage,
        false);

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles validation exceptions when the request body is missing or malformed.
   *
   * @return a ResponseEntity containing an ApiResponse with HTTP status 400
   *         (Bad Request), along with a detailed error message.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse> handleMissingBody() {
    ApiResponse response = new ApiResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Validation failed",
        StringConstants.REQUEST_BODY_MISSING_OR_MALFORMED_MESSAGE,
        false);

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles general exceptions.
   *
   * @param e the exception that was thrown.
   * @return a ResponseEntity containing an ApiResponse with HTTP status 500
   *         (Internal Server Error), along with a detailed error message.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse> handleException(Exception e) {
    ApiResponse response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        StringConstants.GENERAL_ERROR_MESSAGE, e.getMessage(), false);

    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
