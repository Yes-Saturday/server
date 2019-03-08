package com.saturday.web.controller;

import com.saturday.common.domain.FrontEndResponse;
import com.saturday.common.utils.CacheUtil;
import com.saturday.system.service.RsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rsa")
public class RsaController extends BaseController {

    @Autowired
    private RsaService rsaService;

    @GetMapping("/publicKey")
    public FrontEndResponse publicKey() {
        var publicKey = rsaService.getPublicKey(getSessionId());
        return FrontEndResponse.success(publicKey);
    }
}