package com.tuapp.libros.repository;

import com.tuapp.libros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    // Buscar libros por usuario
    List<Libro> findByUsuarioId(Long usuarioId);
    
    // Buscar por título (búsqueda parcial, case-insensitive)
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    
    // Buscar por autor
    List<Libro> findByAutorContainingIgnoreCase(String autor);
    
    // Buscar por ISBN
    Optional<Libro> findByIsbn(String isbn);
    
    // Buscar libros disponibles
    List<Libro> findByDisponible(Boolean disponible);
    
    // Verificar si existe ISBN
    Boolean existsByIsbn(String isbn);
}
