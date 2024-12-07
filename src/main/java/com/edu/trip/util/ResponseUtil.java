package com.edu.trip.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity ok(T data) {
        return new ResponseEntity(data, HttpStatus.OK);
    }
    public static ResponseEntity fail(HttpStatus code, String msg) {
        return new ResponseEntity<>(msg, code);
    }
    public static ResponseEntity fail(String msg) {
        return fail(HttpStatus.BAD_REQUEST, msg);
    }
    public static ResponseEntity failAuth() {
        return fail(HttpStatus.UNAUTHORIZED, "No Permission");
    }

}
