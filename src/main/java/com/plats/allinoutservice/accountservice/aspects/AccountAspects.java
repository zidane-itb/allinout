package com.plats.allinoutservice.accountservice.aspects;

import com.plats.allinoutservice.accountservice.pojo.Secret;
import com.plats.allinoutservice.accountservice.repositories.SecretRepository;
import com.plats.allinoutservice.accountservice.util.PasswordUtil;
import com.plats.allinoutservice.accountservice.pojo.Account;
import com.plats.allinoutservice.accountservice.pojo.LoginResponse;
import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.logging.Logger;

@Aspect
@Component
public class AccountAspects {

    private final PasswordUtil passwordUtil;
    private final SecretRepository secretRepository;
    private Logger logger = Logger.getLogger(AccountAspects.class.getName());

    public AccountAspects(PasswordUtil passwordUtil, SecretRepository secretRepository) {
        this.passwordUtil = passwordUtil;
        this.secretRepository = secretRepository;
    }

    @Before("@annotation(com.plats.allinoutservice.accountservice.aspects.LoginLog)")
    @Order(2)
    public void logLogin(JoinPoint joinPoint) throws Throwable {
        Object[] params = joinPoint.getArgs();
        Account account = (Account) params[0];
        String username = account.getUsername();

        logger.info("Login try detected with username: " + username);


    }

    @Around("@annotation(com.plats.allinoutservice.accountservice.aspects.HashCredential)")
    @Order(1)
    public LoginResponse hashPassword(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] params = joinPoint.getArgs();
        Account account = (Account) params[0];

        // hash and change account instance
        String hashedPassword = passwordUtil.hashPassword(account.getPassword());
        account = account.changeAccountToHashed(hashedPassword);
        Object[] newArgs = {account};

        LoginResponse loginResponse = (LoginResponse) joinPoint.proceed(newArgs);

        return loginResponse;
    }

    @AfterReturning(value = "@annotation(com.plats.allinoutservice.accountservice.aspects.GenerateSecrets)",
                        returning = "response")
    public void generateSecrets_forLogin(JoinPoint joinPoint, Object response) {
        String secretString = UUID.randomUUID().toString();
        if (response instanceof LoginResponse) {
            response = (LoginResponse) response;
            ((LoginResponse) response).setSecretString(secretString);
            secretRepository.save(new Secret(secretString, ((LoginResponse) response).getUsername()));
        }

    }

    @Before("@annotation(com.plats.allinoutservice.accountservice.aspects.SignUpLog)")
    @Order(2)
    public void logSignUp(JoinPoint joinPoint) {
        Object[] params = joinPoint.getArgs();
        Account account = (Account) params[0];
        String usernameRegister = account.getUsername();

        logger.info("Signup detected with username: " + usernameRegister);
    }


}
