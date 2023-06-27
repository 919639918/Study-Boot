package com.example.backend.services.impl;

import com.example.backend.entity.Account;
import com.example.backend.mapper.UserMapper;
import com.example.backend.services.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Value("${spring.mail.username}")
    String from;
    @Resource
    UserMapper mapper;
    @Resource
    MailSender sender;

    @Autowired
    StringRedisTemplate redisTemplate;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) throw new UsernameNotFoundException("请输入用户名!");
        Account account = mapper.findAccountByName(username);
        if (account == null) throw new UsernameNotFoundException("用户名或密码错误!");
        return User.withUsername(account.getUsername()).password(account.getPassword()).roles("user").build();
    }
    @Override
    public boolean sendValidateEmail(String email, String sessionId) {
        String key = "email:" + sessionId + "::" + email;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            Long expire = Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if (expire > 120) {
                return false;
            }
        }
        if (mapper.findAccountByName(email) != null) {
            return false;
        }
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("SYH");
        message.setText("验证码是:" + code);
        try {
            sender.send(message);
            redisTemplate.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public String validateAndRegister(String username, String password, String email, String code, String sessionId) {
        String key = "email:" + sessionId + "::" + email;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            String s = redisTemplate.opsForValue().get(key);
            if (s == null)
                return "验证码失效!";
            if (s.equals(code)) {
                password = encoder.encode(password);
                if (mapper.createAccount(username, password) > 0) {
                    return null;
                } else {
                    return "创建用户失败!";
                }
            } else {
                return "验证码错误!";
            }
        } else {
            return "请先申请验证码!";
        }
    }
}
