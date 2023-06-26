package com.example.backend.controllor;

import com.example.backend.entity.RestBean;
import com.example.backend.services.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    AuthorizeService service;

    @PostMapping("/valid-email")
    public RestBean<String> validateEmail(@RequestParam("email") String email, HttpSession session) {
        if (service.sendValidateEmail(email,session.getId())) {
            return RestBean.success("验证码已经发送到邮箱!");

        } else {
            return RestBean.failure(400, "发送失败!");
        }

    }

}
