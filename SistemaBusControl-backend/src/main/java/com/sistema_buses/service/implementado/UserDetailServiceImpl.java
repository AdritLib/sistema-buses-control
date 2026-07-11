package com.sistema_buses.service.implementado;

import com.sistema_buses.exception.UsuarioNoEncontradoException;
import com.sistema_buses.model.UserDetailsImpl;
import com.sistema_buses.model.Usuario;
import com.sistema_buses.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UsuarioRepository userRepository;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepository.findByCorreo(username).orElseThrow(UsuarioNoEncontradoException::new);
        
        return new UserDetailsImpl(user);
    }
}