package com.example.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class BackEndApplicationTests {

    @Test
    void contextLoads() {

        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        System.out.println(bCrypt.encode("123456"));
    }

}
