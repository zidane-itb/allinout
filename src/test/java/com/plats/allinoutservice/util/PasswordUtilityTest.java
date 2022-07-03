package com.plats.allinoutservice.util;

import com.plats.allinoutservice.accountservice.util.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PasswordUtilityTest {

    private final PasswordUtil passwordUtil;

    @Autowired
    public PasswordUtilityTest(PasswordUtil passwordUtil) {
        this.passwordUtil = passwordUtil;
    }

    @Test
    public void testPasswordHash_giveSameHash() {
        String password1 = "admin";
        String password2 = "admin";

        String hashedpassword2 = passwordUtil.hashPassword(password2);
        System.out.println(hashedpassword2.length());

        assertAll(() -> assertThat(password1).isEqualTo(password2),
                  () -> assertThat(passwordUtil.checkPassword(password1, hashedpassword2)).isTrue());
    }
}
