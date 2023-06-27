package com.example.backend.controllor;

import com.example.backend.entity.RestBean;
import com.example.backend.services.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.hibernate.validator.constraints.Length;
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
        if (service.sendValidateEmail(email, session.getId())) {
            return RestBean.success("验证码已经发送到邮箱!");

        } else {
            return RestBean.failure(400, "发送失败!");
        }

    }
    @PostMapping("/register")
    public RestBean<String> validateEmail(@Length(min = 2, max = 8) @RequestParam("username") String username,
                                          @Length(min = 6, max = 12) @RequestParam("password") String password,
                                          @RequestParam("email") String email,
                                          @Length(min = 6, max = 6) @RequestParam("code") String code,
                                          HttpSession session) {

        String result = service.validateAndRegister(username, password, email, code, session.getId());
        if (result == null) {
            return RestBean.success("注册成功!");
        } else {
            return RestBean.failure(402, result);
        }


    }


}
