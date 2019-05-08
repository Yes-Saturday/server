package com.saturday.web.controller;

import com.saturday.common.domain.FrontResponse;
import com.saturday.system.service.RsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rsa")
public class RsaController extends BaseController {

    @Autowired
    private RsaService rsaService;

    @GetMapping("/publicKey")
    public Object publicKey() {
        var key = rsaService.getPublicKey(getSessionId());
        return FrontResponse.success(key);
    }
}