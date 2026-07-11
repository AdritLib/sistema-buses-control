package com.sistema_buses.client;

import com.sistema_buses.dto.usuario.LoginResponse;
import com.sistema_buses.dto.usuario.UsuarioLogin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-client", url = "${backend.base-url}")
public interface AuthClient {

    @PostMapping("/auth/ingresar")
    LoginResponse login(@RequestBody UsuarioLogin loginRequest);
}