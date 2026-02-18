package com.tuapp.libros.repository;

import com.tuapp.libros.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Spring genera automáticamente la implementación de estos métodos
    
    Optional<Usuario> findByEmail(String email);
    
    Boolean existsByEmail(String email);
}
