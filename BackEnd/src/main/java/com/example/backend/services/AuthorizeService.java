package com.example.backend.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {

    boolean sendValidateEmail(String email,String sessionId);
    String validateAndRegister(String username,String password,String email,String code,String sessionId);


}
