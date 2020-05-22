package com.dongtian.orderingsystem.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
@ControllerAdvice
public class AuthorizeException extends RuntimeException {
    @ExceptionHandler(value = AuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("/user/loginPage");
    }
}
