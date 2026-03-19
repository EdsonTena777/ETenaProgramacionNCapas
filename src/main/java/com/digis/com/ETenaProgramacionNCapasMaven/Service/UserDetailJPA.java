
package com.digis.com.ETenaProgramacionNCapasMaven.Service;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.UsuarioDAOJPAImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailJPA implements UserDetailsService{
    
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
    @Override
    public UserDetails loadUserByUsername(String Username) throws UsernameNotFoundException {
        Result result = usuarioDAOJPAImplementation.GetByUsername(Username);
        System.out.println("Objeto recibido: " + result.object);
        if (!result.correct || result.object == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + Username);
        }

        com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuario = 
            (com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario) result.object;

        String nombreRol = "USER"; 
        if (usuario.getRoles() != null && usuario.getRoles().getNombreRol() != null) {
            nombreRol = usuario.getRoles().getNombreRol();
        } else {
            System.out.println("Advertencia: El usuario no tiene un rol asignado o no se mapeó.");
        }

        boolean isDisabled = (usuario.getStatus() == 0); 

        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .disabled(isDisabled)
                .roles(nombreRol)
                .build();

    }
}
