package com.tuapp.libros.security;

import com.tuapp.libros.model.Usuario;
import com.tuapp.libros.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> 
                    new UsernameNotFoundException("Usuario no encontrado: " + email)
                );

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(new ArrayList<>())  // Sin roles por ahora
                .build();
    }
}
