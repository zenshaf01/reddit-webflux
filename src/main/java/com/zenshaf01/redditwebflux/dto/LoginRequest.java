package com.zenshaf01.redditwebflux.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
