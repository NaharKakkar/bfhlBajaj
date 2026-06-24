package com.example.bfhl.exception;

import com.example.bfhl.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleAll(Exception ex) {
        ResponseDto r = new ResponseDto();
        r.setIs_success(false);
        r.setUser_id("unknown_00000000");
        r.setEmail("");
        r.setRoll_number("");
        r.setOdd_numbers(java.util.Collections.emptyList());
        r.setEven_numbers(java.util.Collections.emptyList());
        r.setAlphabets(java.util.Collections.emptyList());
        r.setSpecial_characters(java.util.Collections.emptyList());
        r.setSum("0");
        r.setConcat_string("");
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }
}
