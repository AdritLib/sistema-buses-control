package com.sistema_buses.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private Long expiresIn;

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }
    public LoginResponse setExpiresIn(Long expiresIn) {
    	this.expiresIn = expiresIn;
    	return this;
    }
}