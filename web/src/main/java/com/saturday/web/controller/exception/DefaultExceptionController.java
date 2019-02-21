package com.saturday.web.controller.exception;

import com.saturday.common.domain.FrontEndResponse;
import com.saturday.common.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class DefaultExceptionController {
    private final FrontEndResponse system_error = FrontEndResponse.fail("SYSTEM_ERROR", "系统内部错误");;

    private final static Logger log = LoggerFactory.getLogger(DefaultExceptionController.class);

    @ExceptionHandler({InternalException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public FrontEndResponse internalException(InternalException e) {
        log.error(e.getDetailMessage(), e);
        return system_error;
    }

    @ExceptionHandler({VerifyException.class, BusinessException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public FrontEndResponse businessException(BaseRuntimeException e) {
        return FrontEndResponse.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({_404Exception.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public void _404(_404Exception e) {
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public FrontEndResponse MaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return system_error;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public FrontEndResponse exception(Exception e) {
        log.error("unknown", e);
        return system_error;
    }
}