package com.tuapp.libros.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO {

    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200)
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 100)
    private String autor;

    @Size(max = 20)
    private String isbn;

    @Min(1000)
    @Max(2100)
    private Integer anioPublicacion;

    @Size(max = 50)
    private String genero;

    private String descripcion;

    private Boolean disponible;

    private Long usuarioId;

    private String usuarioNombre;
}
