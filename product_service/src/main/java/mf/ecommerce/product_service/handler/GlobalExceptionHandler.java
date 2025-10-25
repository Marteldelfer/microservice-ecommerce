package mf.ecommerce.product_service.handler;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.product_service.exception.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("{} caught with error: {}", e.getClass().getSimpleName(), e.getMessage());
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.name())
                .timestamp(Instant.now())
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(TagWithNameAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleTagWithNameAlreadyExistsException(TagWithNameAlreadyExistsException e) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .timestamp(Instant.now())
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CategoryWithNameAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleCategoryWithNameAlreadyExistsException(CategoryWithNameAlreadyExistsException e) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .timestamp(Instant.now())
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(FileSizeExcededLimitException.class)
    public  ResponseEntity<ApiErrorResponse> handleFileSizeExceededLimitException(FileSizeExcededLimitException e) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .timestamp(Instant.now())
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ImageUploadFailedException.class)
    public ResponseEntity<ApiErrorResponse> handleImageUploadFailedException(ImageUploadFailedException e) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .timestamp(Instant.now())
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ImageDeletionFailedException.class)
    public ResponseEntity<ApiErrorResponse> handleImageDeletionFailedException(ImageDeletionFailedException e) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .timestamp(Instant.now())
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ImageSrcDoesNotBelongToProductException.class)
    public ResponseEntity<ApiErrorResponse> handleImageSrcDoesNotBelongToProductException(ImageSrcDoesNotBelongToProductException e) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .timestamp(Instant.now())
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidFileFormatException(InvalidFileFormatException e) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .timestamp(Instant.now())
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .timestamp(Instant.now())
                .errors(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
