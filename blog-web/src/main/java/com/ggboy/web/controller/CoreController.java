package com.ggboy.web.controller;

import com.ggboy.common.domain.FrontEndResponse;
import com.ggboy.core.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/core")
public class CoreController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public FrontEndResponse blogList() {
        return FrontEndResponse.success();
    }
}