package com.cf.controller;

import com.cf.core.exeption.TaControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class GenericController {

    @ExceptionHandler(TaControllerException.class)
    @ResponseBody
    public Map<String, String> errorResponse(TaControllerException ex, HttpServletResponse response) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", String.valueOf(ex.getErrMsg()));

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();

        errorMap.put("errorStackTrace", stackTrace);
        response.setStatus(ex.getHttpStatusCode());

        return errorMap;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, String> errorResponse(Exception ex, HttpServletResponse response) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();

        errorMap.put("errorStackTrace", stackTrace);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return errorMap;
    }


}
