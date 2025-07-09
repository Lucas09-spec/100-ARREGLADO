package com.Proyecto.Model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROYECTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del proyecto", example = "1")
    
    private Long Id_proyecto;

    @Column(nullable = false)
    @Schema(description = "ID del contrato asociado", example = "10")
    
    private Long Id_contrato;

    @Column(nullable = false)
    @Schema(description = "ID del técnico asignado", example = "5")
  
    private Long Id_tecnico;

    @Column(nullable = false)
    @Schema(description = "Fecha del proyecto", example = "2025-06-22")
    @JsonFormat(pattern = "yyyy-MM-dd")
    
    private LocalDate Fecha;

    @Column(nullable = false)
    @Schema(description = "Comentarios del proyecto", example = "Proyecto urgente")
    private String comentarios;

    @Column(nullable = false)
    @Schema(description = "ID del estado actual", example = "2")
    private Long Id_estado;
}
