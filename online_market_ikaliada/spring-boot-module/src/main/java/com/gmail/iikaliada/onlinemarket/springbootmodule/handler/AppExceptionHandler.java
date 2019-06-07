package com.gmail.iikaliada.onlinemarket.springbootmodule.handler;

import com.gmail.iikaliada.onlinemarket.springbootmodule.exception.ArticleNotFoundException;
import com.gmail.iikaliada.onlinemarket.springbootmodule.exception.CustomException;
import com.gmail.iikaliada.onlinemarket.springbootmodule.exception.ItemNotfoundException;
import com.gmail.iikaliada.onlinemarket.springbootmodule.exception.OrderNotFoundException;
import com.gmail.iikaliada.onlinemarket.springbootmodule.exception.ReviewNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ModelAndView handleCustomException(CustomException ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("errCode", ex.getErrCode());
        model.addObject("errMsg", ex.getErrMsg());
        return model;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleSQLException(ConstraintViolationException ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("errMsg", "This email is already exists");
        return model;
    }

    @ExceptionHandler(ItemNotfoundException.class)
    public void itemNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public void articleNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public void orderNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public void reviewNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(HttpServletRequest request, CustomException ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("errCode", ex.getErrCode());
        model.addObject("errMsg", ex.getErrMsg());
        return "error";
    }
}
