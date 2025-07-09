package com.Direccion.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Entity
@Table(name = "Comuna")
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una comuna perteneciente a una región")
public class Comuna {

    @Id
    @Schema(description = "Identificador único de la comuna", example = "1")
    private Long Id_com;

    @Column(nullable = false)
    @Schema(description = "Identificador de la región a la que pertenece la comuna", example = "1", required = true)
    private Long Id_reg;

    @Column(nullable = false)
    @Schema(description = "Nombre de la comuna", example = "Santiago", required = true)
    private String nom_com;
}
