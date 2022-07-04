package com.plats.allinoutservice.accountservice.pojo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class LoginResponse {

    private String secretString;
    private final String username;

}
