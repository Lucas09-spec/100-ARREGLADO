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
@Table(name = "Region")
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una región geográfica")
public class Region {

    @Id
    @Schema(description = "Identificador único de la región", example = "1")
    private Long Id_reg;

    @Column(nullable = false)
    @Schema(description = "Nombre de la región", example = "Metropolitana", required = true)
    private String Nom_reg;
}
