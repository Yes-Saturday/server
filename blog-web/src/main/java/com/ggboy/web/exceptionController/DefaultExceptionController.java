package com.ggboy.web.exceptionController;

import com.ggboy.common.exception.BaseRuntimeException;
import com.ggboy.common.exception.BusinessException;
import com.ggboy.common.exception.InternalException;
import com.ggboy.common.exception.VerifyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DefaultExceptionController {
    private final String system_error = "{'code':'SYSTEM_ERROR','message':'系统内部错误','data':''}";

    private final static Logger log = LoggerFactory.getLogger(DefaultExceptionController.class);

    @ExceptionHandler({InternalException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String internalException(InternalException e) {
        log.error(e.getDetailMessage(), e);
        return system_error;
    }

    @ExceptionHandler({VerifyException.class, BusinessException.class})
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView businessException(BaseRuntimeException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("code", e.getCode());
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("data", "");
        return modelAndView;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String exception(Exception e) {
        log.error("unknown", e);
        return system_error;
    }
}