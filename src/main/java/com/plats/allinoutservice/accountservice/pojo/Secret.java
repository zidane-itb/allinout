package com.plats.allinoutservice.accountservice.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Secret {

    @Id
    private String secretString;
    private String username;
    private boolean active;

    public Secret(String secretString, String username) {
        this.secretString = secretString;
        this.username = username;
    }

    public Secret() {
    }

    public void setActive(byte active) {
        this.active = active != 0;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
