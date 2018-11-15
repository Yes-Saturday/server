package com.ggboy.web.controller;

import com.ggboy.common.utils.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    public static void main(String[] args) {
        System.out.println(Base64Utils.encodeToUrlSafeString(StringUtil.toBytes("Github -> ggboy-shakalaka")));
    }
}