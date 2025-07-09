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
@Table(name = "Direccion")
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una dirección asociada a una comuna y región")
public class Direccion {

    @Id
    @Schema(description = "Identificador único de la dirección", example = "1")
    private Long Id_dir;

    @Column(nullable = false)
    @Schema(description = "Identificador de la región a la que pertenece la dirección", example = "1", required = true)
    private Long Id_reg;

    @Column(nullable = false)
    @Schema(description = "Identificador de la comuna a la que pertenece la dirección", example = "2", required = true)
    private Long Id_com;

    @Column(nullable = false)
    @Schema(description = "Nombre o descripción textual de la dirección", example = "Avenida Siempre Viva 742", required = true)
    private String nom_dir;
}
