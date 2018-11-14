package com.ggboy.web.exceptionController;

import com.alibaba.fastjson.JSON;
import com.ggboy.common.exception.BaseRuntimeException;
import com.ggboy.common.exception.BusinessException;
import com.ggboy.common.exception.InternalException;
import com.ggboy.common.exception.VerifyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class DefaultExceptionController {
    private final String system_error = "{'code':'SYSTEM_ERROR','message':'系统内部错误','data':''}";

    private final static Logger log = LoggerFactory.getLogger(DefaultExceptionController.class);

    @ExceptionHandler({InternalException.class})
    @ResponseStatus(HttpStatus.OK)
    public String internalException(InternalException e) {
        log.error(e.getDetailMessage(), e);
        return system_error;
    }

    @ExceptionHandler({VerifyException.class, BusinessException.class})
    @ResponseStatus(HttpStatus.OK)
    public String businessException(BaseRuntimeException e) {
        Map<String, String> result = new HashMap<>(4);
        result.put("code",e.getCode());
        result.put("message",e.getMessage());
        result.put("data","");
        return JSON.toJSONString(result);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public String exception(Exception e) {
        log.error("unknown", e);
        return system_error;
    }
}