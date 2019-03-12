package com.saturday.web.controller;

import com.saturday.common.annotation.Verify;
import com.saturday.common.utils.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/temp")
public class TempController {

    @PostMapping("/test")
    public void test(@Verify Domain domain) {
//        System.out.println(JsonUtil.toJson(domain));
    }

    @Getter
    @Setter
    public static class Domain {
        private Son[] a;
        private String b;
        private String c;
    }

    @Getter
    @Setter
    public static class Son {
        private String son_a;
        private String son_b;
    }
}