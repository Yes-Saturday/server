package com.ggboy.web.controller;

import com.ggboy.common.annotation.Verify;
import com.ggboy.common.constant.ErrorCodeConstant;
import com.ggboy.common.domain.FrontEndResponse;
import com.ggboy.common.exception.BusinessException;
import com.ggboy.common.utils.BaseRSA;
import com.ggboy.common.utils.CacheUtil;
import com.ggboy.common.utils.PasswordHandler;
import com.ggboy.system.domain.info.PublisherInfo;
import com.ggboy.system.service.PublisherService;
import com.ggboy.web.constant.SystemConstant;
import com.ggboy.web.domain.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/temp")
public class TempController {

    @PostMapping("/login")
    public String login(@Verify LoginRequest loginRequest, HttpServletRequest request, ModelMap modelMap) {

        Object loginError = CacheUtil.get("loginError");
        if (loginError != null) {
            modelMap.put("msg", "login frequently!");
            return "/error";
        }

        if (!"ggboy".equals(loginRequest.getLoginName()) || !"Zzq=123456".equals(loginRequest.getPassword())) {
            CacheUtil.put("loginError", new Object(), 5 * 60);
            modelMap.put("msg", "password is wrong!");
            return "/error";
        }

        var userInfo = new HashMap<String, String>(2);
        userInfo.put("name", loginRequest.getLoginName());
        request.getSession().setAttribute("user", userInfo);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        var session = request.getSession(false);
        if (session != null)
            session.setAttribute("user", null);
        return "redirect:/";
    }
}