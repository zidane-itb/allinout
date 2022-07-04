package com.plats.allinoutservice.accountservice.pojo;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
@AllArgsConstructor
public class Secret {

    @Id
    private String secretString;
    private String username;
    private boolean active;

    public Secret() {
    }
}
