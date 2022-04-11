package com.uin.utils;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testng.annotations.Test;

/**
 * @author wanglufei
 * @description: TODO
 * @date 2022/4/10/6:14 PM
 */
@SpringBootTest
public class PasswordEncoder_test {

    @Test
    public void test(){
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String encode = bc.encode("123");
        System.out.println(encode);
        boolean matches = bc.matches("123", encode);
        System.out.println(matches);
    }
}
