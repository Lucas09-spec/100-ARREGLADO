package com.model;

import java.sql.Date;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "Contraton ")
@Schema(description = "Entidad que representa un contrato de servicio entre un usuario y la empresa")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del contrato", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Fecha en que se realiza el contrato", example = "2025-06-21", required = true)
    private Date fecha_contrato;

    @Column(nullable = false)
    @Schema(description = "ID del usuario que realiza el contrato", example = "10", required = true)
    private Long id_usuario;

    @Column(nullable = false)
    @Schema(description = "ID de la dirección asociada al contrato", example = "5", required = true)
    private Long id_direcc;

    @Column(nullable = false)
    @Schema(description = "Valor total del contrato en moneda local", example = "150000", required = true)
    private Integer total;

    @Column(nullable = false)
    @Schema(description = "ID del servicio contratado", example = "3", required = true)
    private Long id_servicio;

    @Column(nullable = false)
    @Schema(description = "Fecha de inicio del servicio contratado", example = "2025-07-01", required = true)
    private Date fecha_inicio;

    @Column(nullable = false)
    @Schema(description = "Fecha de finalización del servicio contratado", example = "2026-07-01", required = true)
    private Date fecha_final;
}
