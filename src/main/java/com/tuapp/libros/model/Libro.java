package com.tuapp.libros.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    @Column(nullable = false, length = 200)
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 100, message = "El autor no puede exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String autor;

    @Size(max = 20, message = "El ISBN no puede exceder 20 caracteres")
    @Column(unique = true, length = 20)
    private String isbn;

    @Min(value = 1000, message = "Año inválido")
    @Max(value = 2100, message = "Año inválido")
    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    @Size(max = 50, message = "El género no puede exceder 50 caracteres")
    @Column(length = 50)
    private String genero;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private Boolean disponible = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore  // Evita serialización infinita
    private Usuario usuario;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor con parámetros básicos
    public Libro(String titulo, String autor, Usuario usuario) {
        this.titulo = titulo;
        this.autor = autor;
        this.usuario = usuario;
        this.disponible = true;
    }

    // Timestamps automáticos
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
