package com.example.backend.config;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.entity.RestBean;
import com.example.backend.services.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Resource
    AuthorizeService authorizeService;
    @Resource
    DataSource dataSource;

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/api/auth/login")
//                .successHandler(this::onAuthenticationSuccess)
//                .failureHandler(this::onAuthenticationFailure)
//                .and()
//                .logout()
//                .logoutUrl("/api/auth/logout")
//                .logoutSuccessHandler(this::onAuthenticationSuccess)
//                .and()
//                .rememberMe()
//                .rememberMeParameter("remember")
//                .tokenRepository(this.tokenRepository())
//                .tokenValiditySeconds(3600 * 24 * 3)
//                .and()
//                .csrf()
//                .disable()
//                .cors()
//                .configurationSource(this.configurationSource())
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(this::onAuthenticationFailure)
//                .and()
//                .build();
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated();
        http.formLogin().loginProcessingUrl("/api/auth/login").successHandler(this::onAuthenticationSuccess).failureHandler(this::onAuthenticationFailure);
        http.logout().logoutUrl("/api/auth/logout").logoutSuccessHandler(this::onAuthenticationSuccess);
        http.rememberMe().rememberMeParameter("remember").tokenRepository(tokenRepository()).tokenValiditySeconds(3600 * 24 * 3);
        http.csrf().disable();
        http.cors().configurationSource(configurationSource());
        http.exceptionHandling().authenticationEntryPoint(this::onAuthenticationFailure);

        return http.build();
    }
    private CorsConfigurationSource configurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.addAllowedOriginPattern("*");
        cors.setAllowCredentials(true);
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        cors.addExposedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }
    private PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity security) throws Exception {
        return security.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(authorizeService).and().build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setCharacterEncoding("utf-8");

        if (request.getRequestURI().endsWith("/login"))
            response.getWriter().write(JSONObject.toJSONString(RestBean.success("登录成功!")));
        else if (request.getRequestURI().endsWith("/logout"))
            response.getWriter().write(JSONObject.toJSONString(RestBean.success("退出登录成功!")));

    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSONObject.toJSONString(RestBean.failure(302, exception.getMessage())));

    }

}
