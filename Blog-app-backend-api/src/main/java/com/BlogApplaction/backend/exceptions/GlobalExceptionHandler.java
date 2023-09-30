package com.BlogApplaction.backend.exceptions;



import com.BlogApplaction.backend.payload.ApiResponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponce> ResourceNotFoundException(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponce apiResponce = new ApiResponce(message,false);
        return new ResponseEntity<ApiResponce>(apiResponce,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String ,String >> MethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(field,message);
        });

        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponce> handleApiException(ApiException ex) {
        String message = ex.getMessage();
        ApiResponce apiResponse = new  ApiResponce(message, true);
        return new ResponseEntity< ApiResponce>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
