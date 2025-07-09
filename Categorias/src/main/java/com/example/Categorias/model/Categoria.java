package com.example.Categorias.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una categoría de productos o servicios")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la categoría", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre de la categoría", example = "Tecnología", required = true)
    private String nombre;

    @Column
    @Schema(description = "Descripción breve de la categoría", example = "Productos tecnológicos y electrónicos")
    private String descripcion;
}
