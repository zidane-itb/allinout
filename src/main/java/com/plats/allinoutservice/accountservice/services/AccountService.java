package com.plats.allinoutservice.accountservice.services;

import com.plats.allinoutservice.accountservice.exceptions.*;
import com.plats.allinoutservice.accountservice.pojo.LoginResponse;
import com.plats.allinoutservice.accountservice.pojo.Secret;
import com.plats.allinoutservice.accountservice.repositories.SecretRepository;
import com.plats.allinoutservice.accountservice.util.PasswordUtil;
import com.plats.allinoutservice.accountservice.aspects.GenerateSecrets;
import com.plats.allinoutservice.accountservice.aspects.HashCredential;
import com.plats.allinoutservice.accountservice.aspects.LoginLog;
import com.plats.allinoutservice.accountservice.aspects.SignUpLog;
import com.plats.allinoutservice.accountservice.pojo.Account;
import com.plats.allinoutservice.accountservice.repositories.AccountRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final PasswordUtil passwordUtil;
    private final AccountRepository accountRepository;
    private final SecretRepository secretRepository;

    public AccountService(PasswordUtil passwordUtil, AccountRepository accountRepository,
                          SecretRepository secretRepository) {
        this.passwordUtil = passwordUtil;
        this.accountRepository = accountRepository;
        this.secretRepository = secretRepository;
    }

    @PostConstruct
    public void init() {
        // setup admin account
        Account account = new Account("plats", passwordUtil.hashPassword("admin"),
                                    "Zidane", "Firzatullah", true);
        signup(account);

        // setup non-admin account
        Account accountNonAdmin = new Account("plats1", passwordUtil.hashPassword("plats"),
                                        "Zidane", "Firza", false);
        signup(accountNonAdmin);
    }

    @LoginLog
    @GenerateSecrets
    public LoginResponse login(Account account) throws AccountCredentialsNotAvailableException,
            WrongAccountCredentialsException {

        String username = account.getUsername();
        String password = account.getPassword();

        if (username.equals(null) || password.equals(null)) {
            throw new AccountCredentialsNotAvailableException();
        }

        Account accountVerifier = accountRepository.findById(username)
                                    .orElseThrow(() -> new WrongAccountCredentialsException());

        if (secretRepository.existsSecretByUsername(username)) {
            throw new AlreadyLoggedInException();
        }

        if (passwordUtil.checkPassword(password, accountVerifier.getPassword())) {
            return new LoginResponse(true, username);
        }

        throw new WrongAccountCredentialsException();
    }

    @HashCredential
    @SignUpLog
    public void signup(Account account) {
        accountRepository.save(account);
    }

    public void deleteAccount(String secretString, String username) {
        if (verifyRoleAdminBySecretString(secretString)) {
            accountRepository.deleteById(username);
            return;
        }

        throw new NotAdminException();
    }

    private boolean verifyRoleAdminBySecretString(String secretString){
        Secret secretRow = secretRepository.findById(secretString)
                                .orElseThrow(() -> new AccountInvalidException());

        String accountUsername = secretRow.getUsername();
        Account verifyingAccount = accountRepository.findById(accountUsername)
                                    .orElseThrow(() -> new AccountInvalidException());

        if (verifyingAccount.isAdmin()) return true;

        return false;
    }

    public List<Account> getAllAccounts() {
        System.out.println(accountRepository.count());
        List<Account> arr = Streamable.of(accountRepository.findAll()).toList();
        return arr;
    }

}
