package com.saturday.web.controller;

import com.saturday.common.annotation.Verify;
import com.saturday.common.utils.CacheUtil;
import com.saturday.web.domain.request.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            return "error";
        }

        if (!"ggboy".equals(loginRequest.getLoginName()) || !"Zzq=123456".equals(loginRequest.getPassword())) {
            CacheUtil.put("loginError", new Object(), 5 * 60);
            modelMap.put("msg", "password is wrong!");
            return "error";
        }

        var userInfo = new HashMap<String, String>(2);
        userInfo.put("name", loginRequest.getLoginName());
        request.getSession().setAttribute("user", userInfo);
        request.getSession().setMaxInactiveInterval(3600);
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