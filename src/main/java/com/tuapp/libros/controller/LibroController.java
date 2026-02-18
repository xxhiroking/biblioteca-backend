package com.tuapp.libros.controller;

import com.tuapp.libros.dto.LibroDTO;
import com.tuapp.libros.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // LISTAR TODOS LOS LIBROS
    @GetMapping
    public ResponseEntity<List<LibroDTO>> obtenerTodos() {
        List<LibroDTO> libros = libroService.obtenerTodos();
        return ResponseEntity.ok(libros);
    }

    // OBTENER LIBRO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> obtenerPorId(@PathVariable Long id) {
        try {
            LibroDTO libro = libroService.obtenerPorId(id);
            return ResponseEntity.ok(libro);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // OBTENER LIBROS DEL USUARIO AUTENTICADO
    @GetMapping("/mis-libros")
    public ResponseEntity<List<LibroDTO>> obtenerMisLibros() {
        List<LibroDTO> libros = libroService.obtenerLibrosDelUsuario();
        return ResponseEntity.ok(libros);
    }

    // CREAR NUEVO LIBRO
    @PostMapping
    public ResponseEntity<LibroDTO> crearLibro(@Valid @RequestBody LibroDTO libroDTO) {
        try {
            LibroDTO nuevoLibro = libroService.crearLibro(libroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoLibro);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ACTUALIZAR LIBRO
    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> actualizarLibro(
            @PathVariable Long id,
            @Valid @RequestBody LibroDTO libroDTO
    ) {
        try {
            LibroDTO libroActualizado = libroService.actualizarLibro(id, libroDTO);
            return ResponseEntity.ok(libroActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ELIMINAR LIBRO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        try {
            libroService.eliminarLibro(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // BUSCAR LIBROS
    @GetMapping("/buscar")
    public ResponseEntity<List<LibroDTO>> buscarLibros(@RequestParam String q) {
        List<LibroDTO> resultados = libroService.buscarLibros(q);
        return ResponseEntity.ok(resultados);
    }

    // CAMBIAR DISPONIBILIDAD
    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<LibroDTO> cambiarDisponibilidad(@PathVariable Long id) {
        try {
            LibroDTO libro = libroService.cambiarDisponibilidad(id);
            return ResponseEntity.ok(libro);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
