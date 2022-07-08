package com.plats.allinoutservice.accountservice.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity @Data
@AllArgsConstructor @NoArgsConstructor
public class Secret {

    @Id @JsonProperty("secret_string")
    private String secretString;
    private String username;
    private boolean active;

}
