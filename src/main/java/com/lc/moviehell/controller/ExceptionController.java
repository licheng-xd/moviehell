package com.lc.moviehell.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by lc on 16/1/19.
 */
@ControllerAdvice
public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(value = {Exception.class})
    public String handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return "error";
    }
}
