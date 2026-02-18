package com.tuapp.libros.service;

import com.tuapp.libros.dto.LibroDTO;
import com.tuapp.libros.model.Libro;
import com.tuapp.libros.model.Usuario;
import com.tuapp.libros.repository.LibroRepository;
import com.tuapp.libros.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public LibroService(LibroRepository libroRepository, UsuarioRepository usuarioRepository) {
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Obtener usuario autenticado
    private Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    // Convertir Libro a DTO
    private LibroDTO convertirADTO(Libro libro) {
        LibroDTO dto = new LibroDTO();
        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setAutor(libro.getAutor());
        dto.setIsbn(libro.getIsbn());
        dto.setAnioPublicacion(libro.getAnioPublicacion());
        dto.setGenero(libro.getGenero());
        dto.setDescripcion(libro.getDescripcion());
        dto.setDisponible(libro.getDisponible());
        dto.setUsuarioId(libro.getUsuario().getId());
        dto.setUsuarioNombre(libro.getUsuario().getNombre());
        return dto;
    }

    // LISTAR TODOS LOS LIBROS
    public List<LibroDTO> obtenerTodos() {
        return libroRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // OBTENER POR ID
    public LibroDTO obtenerPorId(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
        return convertirADTO(libro);
    }

    // OBTENER LIBROS DEL USUARIO AUTENTICADO
    public List<LibroDTO> obtenerLibrosDelUsuario() {
        Usuario usuario = getUsuarioAutenticado();
        return libroRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // CREAR LIBRO
    @Transactional
    public LibroDTO crearLibro(LibroDTO libroDTO) {
        Usuario usuario = getUsuarioAutenticado();

        // Verificar ISBN duplicado
        if (libroDTO.getIsbn() != null && libroRepository.existsByIsbn(libroDTO.getIsbn())) {
            throw new RuntimeException("Ya existe un libro con ese ISBN");
        }

        // Crear nueva entidad
        Libro libro = new Libro();
        libro.setTitulo(libroDTO.getTitulo());
        libro.setAutor(libroDTO.getAutor());
        libro.setIsbn(libroDTO.getIsbn());
        libro.setAnioPublicacion(libroDTO.getAnioPublicacion());
        libro.setGenero(libroDTO.getGenero());
        libro.setDescripcion(libroDTO.getDescripcion());
        libro.setDisponible(libroDTO.getDisponible() != null ? libroDTO.getDisponible() : true);
        libro.setUsuario(usuario);

        // Guardar
        Libro libroGuardado = libroRepository.save(libro);
        return convertirADTO(libroGuardado);
    }

    // ACTUALIZAR LIBRO
    @Transactional
    public LibroDTO actualizarLibro(Long id, LibroDTO libroDTO) {
        Usuario usuario = getUsuarioAutenticado();

        // Buscar libro
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Verificar que el libro pertenece al usuario autenticado
        if (!libro.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("No tienes permisos para editar este libro");
        }

        // Verificar ISBN duplicado (si cambió)
        if (libroDTO.getIsbn() != null && 
            !libroDTO.getIsbn().equals(libro.getIsbn()) &&
            libroRepository.existsByIsbn(libroDTO.getIsbn())) {
            throw new RuntimeException("Ya existe un libro con ese ISBN");
        }

        // Actualizar campos
        libro.setTitulo(libroDTO.getTitulo());
        libro.setAutor(libroDTO.getAutor());
        libro.setIsbn(libroDTO.getIsbn());
        libro.setAnioPublicacion(libroDTO.getAnioPublicacion());
        libro.setGenero(libroDTO.getGenero());
        libro.setDescripcion(libroDTO.getDescripcion());
        libro.setDisponible(libroDTO.getDisponible());

        // Guardar cambios
        Libro libroActualizado = libroRepository.save(libro);
        return convertirADTO(libroActualizado);
    }

    // ELIMINAR LIBRO
    @Transactional
    public void eliminarLibro(Long id) {
        Usuario usuario = getUsuarioAutenticado();

        // Buscar libro
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Verificar que el libro pertenece al usuario autenticado
        if (!libro.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("No tienes permisos para eliminar este libro");
        }

        // Eliminar
        libroRepository.delete(libro);
    }

    // BUSCAR LIBROS
    public List<LibroDTO> buscarLibros(String query) {
        // Buscar por título o autor (case-insensitive)
        List<Libro> librosPorTitulo = libroRepository.findByTituloContainingIgnoreCase(query);
        List<Libro> librosPorAutor = libroRepository.findByAutorContainingIgnoreCase(query);

        // Combinar resultados (sin duplicados)
        List<Libro> resultados = librosPorTitulo;
        for (Libro libro : librosPorAutor) {
            if (!resultados.contains(libro)) {
                resultados.add(libro);
            }
        }

        return resultados.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // CAMBIAR DISPONIBILIDAD
    @Transactional
    public LibroDTO cambiarDisponibilidad(Long id) {
        Usuario usuario = getUsuarioAutenticado();

        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        if (!libro.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("No tienes permisos para modificar este libro");
        }

        // Alternar disponibilidad
        libro.setDisponible(!libro.getDisponible());
        Libro libroActualizado = libroRepository.save(libro);
        
        return convertirADTO(libroActualizado);
    }
}
