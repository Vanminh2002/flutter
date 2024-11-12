package org.example.apiflutter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.NoSuchAlgorithmException;

@SpringBootTest
class ApiFlutterApplicationTests {

    private static final Logger log = LogManager.getLogger(ApiFlutterApplicationTests.class);

    @Test
    void hash () throws NoSuchAlgorithmException {
        String password = "123455";

         PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        log.info("Bcrypt round 1 :{}", passwordEncoder.encode(password));
        log.info("Bcrypt round 2 :{}", passwordEncoder.encode(password));
    }

}
