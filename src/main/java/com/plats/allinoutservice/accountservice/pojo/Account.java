package com.plats.allinoutservice.accountservice.pojo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Account {

    @Id
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean admin;
    private BigDecimal balance;

    public Account(String username, String password, String firstName, String lastName, boolean admin) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = new BigDecimal(0);
        this.admin = admin;
    }

    public Account changeAccountToHashed(@NonNull String hashedPassword) {
        Account newAccount = new Account(this.username, hashedPassword,
                                            this.firstName, this.lastName, this.admin, this.balance);

        return newAccount;
    }
}
