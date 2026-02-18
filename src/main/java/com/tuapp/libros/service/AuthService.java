package com.tuapp.libros.service;

import com.tuapp.libros.dto.AuthResponse;
import com.tuapp.libros.dto.LoginRequest;
import com.tuapp.libros.dto.RegisterRequest;
import com.tuapp.libros.model.Usuario;
import com.tuapp.libros.repository.UsuarioRepository;
import com.tuapp.libros.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        // Guardar en BD
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Generar token
        String token = jwtUtil.generateToken(usuarioGuardado.getEmail());

        // Retornar respuesta
        return new AuthResponse(
                token,
                usuarioGuardado.getEmail(),
                usuarioGuardado.getNombre(),
                usuarioGuardado.getId()
        );
    }

    public AuthResponse login(LoginRequest request) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        // Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // Generar token
        String token = jwtUtil.generateToken(usuario.getEmail());

        // Retornar respuesta
        return new AuthResponse(
                token,
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getId()
        );
    }
}
