package com.saturday.web.controller.exception;

import com.saturday.common.domain.FrontResponse;
import com.saturday.common.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class DefaultExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    private final static FrontResponse system_error = FrontResponse.fail("系统内部错误");

    @ExceptionHandler({InternalException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object internalException(InternalException e) {
        log.error(e.getDetailMessage(), e);
        return system_error;
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object businessException(BusinessException e) {
        return FrontResponse.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({AuthorityException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Object AuthorityException(AuthorityException e) {
        return FrontResponse.fail(e.getCode(), null);
    }

    @ExceptionHandler({VerifyException.class})
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public Object verifyException(VerifyException e) {
        return FrontResponse.fail(e.getMessage());
    }

    @ExceptionHandler({_404Exception.class, NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Object _404Exception(Exception e) {
        return FrontResponse.fail("404 Not Found");
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.REQUEST_ENTITY_TOO_LARGE)
    @ResponseBody
    public Object MaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return FrontResponse.fail("文件大小超出限制");
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object exception(Exception e) {
        log.error("unknown", e);
        return system_error;
    }
}