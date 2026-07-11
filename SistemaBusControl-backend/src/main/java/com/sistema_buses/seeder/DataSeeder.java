package com.sistema_buses.seeder;

import com.sistema_buses.model.Rol;
import com.sistema_buses.model.Usuario;
import com.sistema_buses.enums.Roles;
import com.sistema_buses.enums.TipoDocumento;
import com.sistema_buses.repository.RolRepository;
import com.sistema_buses.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    	loadRoles();
    	loadRootUser();
    	loadConductorUser();
    	loadSupervisorUser();
    }

    private void loadRoles() {
        Roles[] roleNames = new Roles[] { Roles.CONDUCTOR, Roles.SUPERVISOR, Roles.ADMIN };
        Map<Roles, String> roleDescriptionMap = Map.of(
        		Roles.CONDUCTOR, "Usuario que conduce un vehiculo.",
        		Roles.SUPERVISOR, "Usuario que supervisa las estaciones y la condicion de los buses",
        		Roles.ADMIN, "Usuario con acceso a los registros y manejo del sistema"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Rol> rol = rolRepository.findByNombre(roleName);
            if(rol.isEmpty()) {
                Rol nuevoRol = new Rol();

                nuevoRol.setNombre(roleName);
                nuevoRol.setDescripcion(roleDescriptionMap.get(roleName));

                rolRepository.save(nuevoRol);
            }
        });
    }
    
    private void loadRootUser() {
    	if(usuarioRepository.findByCorreo("root@noemail.com").isPresent()) return;
    	Rol rol = rolRepository.findByNombre(Roles.ADMIN).orElseThrow();
    	
    	Usuario usuario = new Usuario();
    	usuario.setNombre("root");
    	usuario.setCorreo("root@noemail.com");
    	usuario.setTelefono("100100100");
    	usuario.setTipoDocumento(TipoDocumento.DNI);
    	usuario.setNumDocumento("10010022");
    	usuario.setClave(passwordEncoder.encode("root_app_1"));
    	usuario.setRol(rol);
    	
    	usuarioRepository.save(usuario);
    }
    private void loadConductorUser() {
    	if(usuarioRepository.findByCorreo("hrsjaura@email.com").isPresent()) return;
    	Rol rol = rolRepository.findByNombre(Roles.CONDUCTOR).orElseThrow();
    	
    	Usuario usuario = new Usuario();
    	usuario.setNombre("Herson Jaura");
    	usuario.setCorreo("hrsjaura@email.com");
    	usuario.setTelefono("100300200");
    	usuario.setTipoDocumento(TipoDocumento.DNI);
    	usuario.setNumDocumento("50010024");
    	usuario.setClave(passwordEncoder.encode("doc123"));
    	usuario.setRol(rol);
    	
    	usuarioRepository.save(usuario);
    }
    private void loadSupervisorUser() {
    	if(usuarioRepository.findByCorreo("juanherrera@email.com").isPresent()) return;
    	Rol rol = rolRepository.findByNombre(Roles.SUPERVISOR).orElseThrow();
    	
    	Usuario usuario = new Usuario();
    	usuario.setNombre("Juan Herrera");
    	usuario.setCorreo("juanherrera@email.com");
    	usuario.setTelefono("100500400");
    	usuario.setTipoDocumento(TipoDocumento.DNI);
    	usuario.setNumDocumento("10050624");
    	usuario.setClave(passwordEncoder.encode("sup123"));
    	usuario.setRol(rol);
    	
    	usuarioRepository.save(usuario);
    }
}
